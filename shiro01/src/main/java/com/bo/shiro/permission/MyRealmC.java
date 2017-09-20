package com.bo.shiro.permission;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 自定义授权realm
 * @author 王博
 * @version 2017年9月20日　下午8:00:17
 */
public class MyRealmC extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(MyRealmC.class);
	
	/**
	 * 
	 */
	public MyRealmC() {
	}

	/**
	 * @param cacheManager
	 */
	public MyRealmC(CacheManager cacheManager) {
		super(cacheManager);
	}

	/**
	 * @param matcher
	 */
	public MyRealmC(CredentialsMatcher matcher) {
		super(matcher);
	}

	/**
	 * @param cacheManager
	 * @param matcher
	 */
	public MyRealmC(CacheManager cacheManager, CredentialsMatcher matcher) {
		super(cacheManager, matcher);
	}

	/**
	 * 获取认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("The method doGetAuthenticationInfo() was invoke.");
		String principal = String.valueOf(token.getPrincipal());
		String credentials = new String((char[])token.getCredentials());
		return new SimpleAuthenticationInfo(principal, credentials, this.getClass().getName());
	}
	
	/**
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("The method doGetAuthorizationInfo() was invoke.");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        /**
         * 只有当SimpleAuthorizationInfo中设置了权限后，自定义的Permission中implies方法才会被调用。
         */
		authorizationInfo.addStringPermission("system-edit-10");
		return authorizationInfo;
	}

}
