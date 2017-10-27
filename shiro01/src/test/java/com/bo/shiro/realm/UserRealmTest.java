package com.bo.shiro.realm;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.Assert;
import org.junit.Test;

import com.bo.shiro.service.ServiceTest;

/**
 * @Description
 * @author 王博
 * @version 2017年10月27日　下午2:07:48
 */
public class UserRealmTest extends ServiceTest {

//	@Test
	public void testLoginSuccess() {
		login("classpath:shiro/authc/shiro_user_realm.ini", u1.getUsername(), password);
		Assert.assertTrue(subject().isAuthenticated());
	}

//	@Test(expected = UnknownAccountException.class)
	public void testLoginFailWithUnknownUsername() {
		login("classpath:shiro/authc/shiro_user_realm.ini", u1.getUsername() + "1", password);
	}

//	@Test(expected = IncorrectCredentialsException.class)
	public void testLoginFailWithErrorPassowrd() {
		login("classpath:shiro/authc/shiro_user_realm.ini", u1.getUsername(), password + "1");
	}

//	@Test(expected = LockedAccountException.class)
	public void testLoginFailWithLocked() {
		login("classpath:shiro/authc/shiro_user_realm.ini", u4.getUsername(), password + "1");
	}

//	@Test(expected = ExcessiveAttemptsException.class)
	public void testLoginFailWithLimitRetryCount() {
		for (int i = 1; i <= 5; i++) {
			try {
				login("classpath:shiro/authc/shiro_user_realm.ini", u3.getUsername(), password + "1");
			} catch (Exception e) {
				/* ignore */
			}
		}
		login("classpath:shiro/authc/shiro_user_realm.ini", u3.getUsername(), password + "1");

		// 需要清空缓存，否则后续的执行就会遇到问题(或者使用一个全新账户测试)
	}

//	@Test
	public void testHasRole() {
		login("classpath:shiro/authc/shiro_user_realm.ini", u1.getUsername(), password);
		Assert.assertTrue(subject().hasRole("admin"));
	}

//	@Test
	public void testNoRole() {
		login("classpath:shiro/authc/shiro_user_realm.ini", u2.getUsername(), password);
		Assert.assertFalse(subject().hasRole("admin"));
	}

//	@Test
	public void testHasPermission() {
		login("classpath:shiro/authc/shiro_user_realm.ini", u1.getUsername(), password);
		Assert.assertTrue(subject().isPermittedAll("user:create", "menu:create"));
	}

//	@Test
	public void testNoPermission() {
		login("classpath:shiro/authc/shiro_user_realm.ini", u2.getUsername(), password);
		Assert.assertFalse(subject().isPermitted("user:create"));
	}

}
