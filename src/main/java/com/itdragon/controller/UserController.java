package com.itdragon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itdragon.pojo.User;
import com.itdragon.utils.CodeUtil;
import com.itdragon.utils.CookieUtils;
import com.itdragon.utils.ItdragonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itdragon.pojo.ItdragonResult;
import com.itdragon.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static Logger log = Logger.getLogger(Object.class);
	public static final String COOKIE_NAME = "USER_TOKEN";

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/addUser")
	public void registerUser() {
		User user = new User();
		user.setAccount("jake");
		user.setName("make");
		user.setEmail("itdragon@git.com");
		user.setTelephone("18815621455");
		user.setPlainPassword("123456");
		user.setPlatform("github");
		user.setUpdatedDate(ItdragonUtils.getCurrentDateTime());
		ItdragonUtils.entryptPassword(user);
		System.out.println(user);
		userService.registerUser(user);
	}

	@RequestMapping(value="/sendCode")
	@ResponseBody
	public ItdragonResult sendCode(HttpServletRequest request){
		log.info("=====================>> 发送激活码到邮箱 begin .. ");
		ItdragonResult itdragonResult = userService.sendCode(request);
		return itdragonResult;
	}

	@RequestMapping(value="/resetPassWord", method=RequestMethod.POST)
	@ResponseBody
	public ItdragonResult resetPassWord(String password,String code, HttpServletRequest request){
		log.info("=====================>> 用户修改密码 begin .. ");

		ItdragonResult itdragonResult = userService.resetPassWord(password,code, request);
		return itdragonResult;
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ItdragonResult userLogin(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("=====================>> soo 服务 用户未登录, 下面进行登录 ... ");
		try {
			ItdragonResult result = userService.userLogin(username, password, request, response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ItdragonResult.build(500, "");
		}
	}
	
	@RequestMapping(value="/logout/{token}")
	public String logout(@PathVariable String token) {
		userService.logout(token); // 思路是从Redis中删除key，实际情况请和业务逻辑结合
		return "index";
	}

	@RequestMapping(value="/loginout")
	@ResponseBody
	public ItdragonResult logout(HttpServletRequest request) {
		// 获取cookie的值 , cookie的值就是 token
		String token = CookieUtils.getCookieValue(request, COOKIE_NAME);

		userService.logout(token); // 思路是从Redis中删除key，实际情况请和业务逻辑结合
		return ItdragonResult.build(200,"注销成功");
	}
	
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token) {
		log.info("======================= soo 服务, 从redis中获取响应token的值, token为 :"+ token);
		ItdragonResult result = null;
		try {
			result = userService.queryUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = ItdragonResult.build(500, "");
		}
		log.info("======================>> soo 服务, 封装查询结果, 将sso查询结果返回, "+result.getData().toString());
		return result;
	}


}
