package com.itdragon.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.itdragon.dao.UserDao;
import com.itdragon.pojo.UserQuery;
import com.itdragon.pojo.UserVo;
import com.itdragon.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itdragon.pojo.ItdragonResult;
import com.itdragon.pojo.User;
import com.itdragon.repository.JedisClient;

@Service
@Transactional
@PropertySource(value = "classpath:redis.properties")
public class UserService {
	private static Logger log = Logger.getLogger(Object.class);
//	@Autowired
//	private UserRepository userRepository;

	@Resource
	private UserDao userDao;

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;
	
    public ItdragonResult registerUser(User user) {
    	// 检查用户名是否注册，一般在前端验证的时候处理，因为注册不存在高并发的情况，这里再加一层查询是不影响性能的
//    	if (null != userRepository.findByAccount(user.getAccount())) {
//    		return ItdragonResult.build(400, "");
//    	}
//    	userRepository.save(user);

		UserQuery userQuery = new UserQuery();
		userQuery.setAccount(user.getAccount());
		List<UserVo> userList = userDao.getUserList(userQuery);
		if (userList!=null && userList.size()>0){
			return ItdragonResult.build(400, "");
		}

		userDao.addUser(user);
		// 注册成功后选择发送邮件激活。现在一般都是短信验证码
    	return ItdragonResult.build(200, "");
    }

    public ItdragonResult sendCode(HttpServletRequest request){
		// 获取当前登录用户
		User user = (User)request.getSession().getAttribute("user");
		if (user == null){
			return ItdragonResult.build(400, "用户未登录状态");
		}else if (user.getEmail() == null || user.getEmail().trim().equals("")){
		    return ItdragonResult.build(400, "未绑定邮箱,不能修改密码");
        }

		//生成激活码
		String code= CodeUtil.generateUniqueCode();
		// 将激活码写入到数据库
		user.setCode(code);
		try {
			userDao.updateUserByKey(user);
			// 发送激活码到邮箱
            new Thread(new MailUtil(user.getEmail(), code)).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
        return ItdragonResult.build(200,"激活码发送成功");
	}

    public ItdragonResult resetPassWord(String password,String code, HttpServletRequest request){
		// 获取当前登录用户
		User user = (User)request.getSession().getAttribute("user");
		if (user == null){
			return ItdragonResult.build(400, "用户未登录状态");
		}
		if (StringUtils.isEmpty(password)){
			return ItdragonResult.build(400, "新的密码不能为空");
		}
		if (StringUtils.isEmpty(code)){
    		return ItdragonResult.build(400, "请输入激活码");
		}

		// 验证激活码是否正确
		UserQuery userQuery = new UserQuery();
		userQuery.setAccount(user.getAccount());
		userQuery.setCode(code);
		List<UserVo> userList = userDao.getUserList(userQuery);
		if (userList!= null && userList.size()>0){

			UserVo userVo = userList.get(0);
			// 修改密码
			String md5Password = ItdragonUtils.getmd5Password(userVo, password);
			userVo.setPassWord(md5Password);
			try {
				userDao.updateUserByKey(userVo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ItdragonResult.build(200,"修改成功");
		}else {
			return ItdragonResult.build(400, "激活码错误");
		}
	}
    
    public ItdragonResult userLogin(String account, String password,
			HttpServletRequest request, HttpServletResponse response) {
    	// 判断账号密码是否正确
//		User user = userRepository.findByAccount(account);

		UserQuery userQuery = new UserQuery();
		userQuery.setAccount(account);
		List<UserVo> userList = userDao.getUserList(userQuery);

		if (userList!=null && userList.size()>0){
			User user = userList.get(0);
			Integer role = user.getRole();
//			1 系统管理员 2 项目经理
			if (! (role == 1 || role == 2)){
				return ItdragonResult.build(400, "非管理人员或项目经理禁止登录系统");
			}

			//验证密码是否正确
			if (!ItdragonUtils.decryptPassword(user, password)) {
				return ItdragonResult.build(400, "密码错误");
			}

			log.info("=============================>>sso登录服务器, 用户登录成功, 帐号密码正确");

			// 生成token
			String token = UUID.randomUUID().toString();
			// 清空密码和盐避免泄漏
			String userPassword = user.getPassWord();
			String userSalt = user.getSalt();
			user.setPassWord(null);
			user.setSalt(null);
			// 把用户信息写入 redis
			jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
			// user 已经是持久化对象了，被保存在了session缓存当中，若user又重新修改了属性值，那么在提交事务时，
			// 此时 hibernate对象就会拿当前这个user对象和保存在session缓存中的user对象进行比较，如果两个对象相同，
			// 则不会发送update语句，否则，如果两个对象不同，则会发出update语句。
			user.setPassWord(userPassword);
			user.setSalt(userSalt);
			// 设置 session 的过期时间
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
			// 添加写 cookie 的逻辑，cookie 的有效期是关闭浏览器就失效。
			CookieUtils.setCookie(request, response, "USER_TOKEN", token);
			// 返回token
			return ItdragonResult.ok(token);

		}else{
			return ItdragonResult.build(400, "账号名错误");
		}
	}
    
    public void logout(String token) {
    	jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
    }

	public ItdragonResult queryUserByToken(String token) {
		// 根据token从redis中查询用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		log.info("========================>> sso服务, 使用token值:"+token+",从redis中获取的值为 :"+json);
		// 判断是否为空
		if (StringUtils.isEmpty(json)) {
			return ItdragonResult.build(400, "此session已经过期，请重新登录");
		}
		// 更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		// 返回用户信息
		return ItdragonResult.ok(JsonUtils.jsonToPojo(json, User.class));
	}
    
}
