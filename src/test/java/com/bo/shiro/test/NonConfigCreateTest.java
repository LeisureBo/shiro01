package com.bo.shiro.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.log4j.PropertyConfigurator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Description 纯java代码配置shiro
 * @see 等价的ini文件配置shiro_config.ini
 * @author 王博
 * @version 2017年10月14日　上午10:44:00
 */
public class NonConfigCreateTest {

	private static Logger logger;
	
	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger = LoggerFactory.getLogger(NonConfigCreateTest.class);
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

	@Test
	public void test() {
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		// 设置auhenticator
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		securityManager.setAuthenticator(authenticator);
		
		// 设置authorizer
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
		authorizer.setPermissionResolver(new WildcardPermissionResolver());
		securityManager.setAuthorizer(authorizer);
		
		// 设置realm
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/shiro");
		ds.setUsername("root");
		ds.setPassword("root");
		
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(ds);
		jdbcRealm.setPermissionsLookupEnabled(true);
		securityManager.setRealms(Arrays.asList((Realm)jdbcRealm));
		
		// 将SecurityManager设置到SecurityUtils 方便全局使用
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("bo","12345");
		subject.login(token);
		
		assertTrue("用户名或密码错误!",subject.isAuthenticated());
	}

}
