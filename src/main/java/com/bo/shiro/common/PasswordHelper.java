package com.bo.shiro.common;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.bo.shiro.entity.User;

/**
 * @Description 密码加密工具
 * @author 王博
 * @version 2017年10月18日　下午5:12:27
 */
public class PasswordHelper {
	
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	private String algorithmName = "md5";
	private final int hashIterations = 2;

	public void encryptPassword(User user) {

		user.setSalt(randomNumberGenerator.nextBytes().toHex());

		String newPassword = new SimpleHash(
				algorithmName, 
				user.getPassword(), 
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				hashIterations).toHex();

		user.setPassword(newPassword);
	}
}
