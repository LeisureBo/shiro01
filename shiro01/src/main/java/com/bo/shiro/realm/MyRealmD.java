package com.bo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Description 自定义密码加密realm
 * @author 王博
 * @version 2017年10月14日　下午2:49:52
 */
public class MyRealmD extends AuthorizingRealm {

	private PasswordService passwordService;
	
	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		/*
		 * MyRealmD间接继承了AuthenticatingRealm，
		 * 其在调用getAuthenticationInfo方法获取到AuthenticationInfo信息后
		 * 会使用credentialsMatcher来验证凭据是否匹配
		 * 如果不匹配将抛出IncorrectCredentialsException异常。
		 */
		return new SimpleAuthenticationInfo(
                "bo",
                passwordService.encryptPassword("12345"),
                getName());
	}

}
