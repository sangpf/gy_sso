package com.itdragon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.util.DigestUtils;

import com.itdragon.pojo.User;
import org.springframework.util.StringUtils;


/**
 * 工具类
 * @author itdragon
 *
 */
public class ItdragonUtils {
	
	/**
	 * 加盐加密的策略非常多,根据实际业务来
	 */
	public static void entryptPassword(User user) {
		String salt = UUID.randomUUID().toString();
		String temPassword = salt + user.getPlainPassword();
		String md5Password = DigestUtils.md5DigestAsHex(temPassword.getBytes());
		user.setSalt(salt);
		user.setPassWord(md5Password);
	}

	/**
	 *
	 * @param user			user对象
	 * @param plainPassword  用户输入的未加密密码
	 * @return
	 * md5Password  加密后的密码
	 */
	public static boolean decryptPassword(User user, String plainPassword) {
		String temPassword = user.getSalt() + plainPassword;
		String md5Password = DigestUtils.md5DigestAsHex(temPassword.getBytes());
		return user.getPassWord().equals(md5Password);
	}

	public static String getmd5Password(User user, String plainPassword){
		String temPassword = user.getSalt() + plainPassword;
		String md5Password = DigestUtils.md5DigestAsHex(temPassword.getBytes());
		return md5Password;
	}
	
	public static String getCurrentDateTime() {
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(zone);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

}
