package com.bo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/**
 * @Description 自定义realm实现
 * @author 王博
 * @version 2017年9月19日　下午5:13:51
 */
public class MyRealmB implements Realm {

	/**
	 * 返回一个唯一的Realm名字
	 */
	@Override
	public String getName() {
		return this.getClass().getName();
	}

	/**
	 * 判断此Realm是否支持此Token
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		// 仅支持UsernamePasswordToken类型的Token
		return token instanceof UsernamePasswordToken;
	}

	/**
	 * 根据Token获取认证信息
	 */
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String principal = String.valueOf(token.getPrincipal());// 得到身份(用户名)
		String credentials = new String((char[])token.getCredentials());// 得到认证/凭证(密码)
//		System.out.println("principal : " + principal + ", credentials : " + credentials + "");
		if (!"bo@163.com".equals(principal)) {
			System.out.println("principal '" + principal + "' not match 'bo@163.com'.");
			throw new UnknownAccountException("用户名/密码错误"); // 如果用户名错误
		}
		if (!"12345".equals(credentials)) {
			System.out.println("credentials '" + credentials + "' not match '12345'.");
			throw new IncorrectCredentialsException("用户凭证错误"); // 如果密码错误
		}
		// 如果身份认证验证成功，返回一个AuthenticationInfo实现；
		return new SimpleAuthenticationInfo(principal, credentials, this.getName());
	}
}
