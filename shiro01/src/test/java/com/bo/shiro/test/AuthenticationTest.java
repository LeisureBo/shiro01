package com.bo.shiro.test;

import static org.junit.Assert.*;

import org.apache.log4j.PropertyConfigurator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description shiro身份验证测试
 * @author 王博
 * @version 2017年9月14日　下午11:38:26
 */
public class AuthenticationTest {

	private static Logger logger;
	
	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger = LoggerFactory.getLogger(AuthenticationTest.class);
		// 加载log4j配置文件(相对路径:工程根目录)
		PropertyConfigurator.configure("src/main/resources/config/log4j.properties"); 
	}

	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 单个realm测试
	 */
	@SuppressWarnings("deprecation")
	@Ignore
	@Test
	public void testAuthenticatorSingleRealm() {
		// 通过new IniSecurityManagerFactory并指定一个ini配置文件来创建一个SecurityManager工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/basic/jdbc_realm.ini");
		// 获取SecurityManager实例
		SecurityManager securityManager = factory.getInstance();
		System.out.println("security manager is " + securityManager + ".");
		// 把securityManager实例绑定到SecurityUtils(这是一个全局设置,设置一次即可)
		SecurityUtils.setSecurityManager(securityManager);
		// 通过SecurityUtils得到Subject，其会自动绑定到当前线程
		Subject currentUser = SecurityUtils.getSubject();
		// 创建token令牌:用户名/密码
		UsernamePasswordToken token = new UsernamePasswordToken("shiro01","12345");
		try {
			// 调用subject.login方法进行登录/身份验证，其会自动委托给SecurityManager.login方法进行登录
			currentUser.login(token);
//			System.out.println("login success!");
		} catch (AuthenticationException e) {
			// 身份验证失败
			logger.info("用户身份验证失败");
			e.printStackTrace();
//			System.out.println("login failed!");
		}
		
		if (currentUser.isAuthenticated()) {
	        logger.info("用户登录成功。");
	    } else {
	        logger.info("用户登录失败。");
	    }
		
		// 断言用户已经登录
		assertEquals("登录异常", true, currentUser.isAuthenticated());
		
		// 退出
		currentUser.logout();
	}
	
	/**
	 * 多个自定义realm测试
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testAuthenticatorMultiRealm(){
		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
	    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/authentication/custom_authenticator_multi_realm.ini");
	     
	    // 2、得到SecurityManager实例并绑定给SecurityUtils
	    SecurityManager securityManager = factory.getInstance();
	    SecurityUtils.setSecurityManager(securityManager);
	     
	    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
	    Subject subject = SecurityUtils.getSubject();
	    /* 
	     * 用户身份Token 可能不仅仅是用户名/密码，也可能还有其他的，如登录时允许用户名/邮箱/手机号同时登录。
	     */
	    UsernamePasswordToken token = new UsernamePasswordToken("bo@163.com", "12345");
	    
	    try{
	        // 4、登录，即身份验证
	        subject.login(token);
	    } catch (AuthenticationException e) {
	        // 5、身份验证失败
	        logger.info("用户身份验证失败");
	        e.printStackTrace();
	    }
	     
	    if (subject.isAuthenticated()) {
	        logger.info("用户登录成功。");
	    } else {
	        logger.info("用户登录失败。");
	    }
	 
	    // 断言用户已经登录
	 	assertEquals("登录异常", true, subject.isAuthenticated());
	    
	    // 6、退出
	    subject.logout();
	}

}
