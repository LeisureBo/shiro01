package com.bo.shiro.authc.credential;

import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.format.HashFormat;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.util.ByteSource;

/**
 * @Description 自定义PasswordService实现
 * PasswordMatcher是依靠PasswordService来实现加密和匹配的，所以我们可以自定义一个PasswordService来按照我们自己约定的加密规则来实现加密
 * @author 王博
 * @version 2017年10月17日　上午11:28:57
 */
public class MyPasswordService implements PasswordService {

	private String algorithmName = "MD5";
	private int iterations = 5;
	private String salt = "2014";

	private HashFormat hashFormat = new Shiro1CryptFormat();

	@Override
	public String encryptPassword(Object plaintextPassword) throws IllegalArgumentException {
		Hash hash = new SimpleHash(algorithmName, ByteSource.Util.bytes(plaintextPassword), ByteSource.Util.bytes(salt), iterations);
		return hashFormat.format(hash);
	}

	@Override
	public boolean passwordsMatch(Object submittedPlaintext, String encrypted) {
		Hash hash = new SimpleHash(algorithmName, ByteSource.Util.bytes(submittedPlaintext), ByteSource.Util.bytes(salt), iterations);
		String password = hashFormat.format(hash);
		return encrypted.equals(password);
	}

}
