package com.bo.shiro.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.log4j.PropertyConfigurator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description shiro授权测试
 * @author 王博
 * @version 2017年9月19日　下午10:23:02
 */
public class AuthorizationTest {

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

	@Test
	public void test() {
		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
	    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/authorization/custom_authorize_permission.ini");
	    
	    // 2、得到SecurityManager实例并绑定给SecurityUtils
	    SecurityManager securityManager = factory.getInstance();
	    SecurityUtils.setSecurityManager(securityManager);
	    
	    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
	    Subject subject = SecurityUtils.getSubject();
	    
	    UsernamePasswordToken token = new UsernamePasswordToken("bo", "12345");
	    try{
	        // 4、登录，即身份验证
	        subject.login(token);
	    } catch (AuthenticationException e) {
	        // 5、身份验证失败
	        logger.info("用户身份验证失败");
	        e.printStackTrace();
	    }
	    
	    // 用户身份得到确认
	    if (subject.isAuthenticated()) {
	        logger.info("用户登录成功。");
	        /** 进行权限判断 */
//	        this.testWhetherHasRole(subject);
	        this.testWhetherHasPermission(subject);
//	        this.testCustomizeAuthorize(subject);
	    } else {
	        logger.info("用户登录失败。");
	    }
	    
	    // 6、退出
	    subject.logout();
	}
	
	/**
	 * @Description 基于角色的访问控制
	 * @param subject
	 */
	public void testWhetherHasRole(Subject subject){
		/** Shiro提供了hasRole/isPermitted用于判断用户是否拥有某个角色/某个权限
		 *	但是没有提供诸如hashAnyRole用于判断是否有某些角色(权限)中的某一个的方法 **/
        // 判断拥有角色：role1
        Assert.assertTrue("断言错误", subject.hasRole("role1"));
        // 判断拥有角色：role1 and role2
        Assert.assertTrue("断言错误", subject.hasAllRoles(Arrays.asList("role1", "role2")));
        // 判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertEquals("断言错误", true, result[0]);
        Assert.assertEquals("断言错误", true, result[1]);
        Assert.assertEquals("断言错误", false, result[2]);
        // 断言拥有角色：role1
        subject.checkRole("role1");
        // 断言拥有角色：role1 and role3 失败抛出异常
        subject.checkRoles("role1", "role3");
	}
	
	/**
	 * @Description 基于权限的访问控制
	 * @param subject
	 */
	public void testWhetherHasPermission(Subject subject){
	        // 判断拥有权限：user:create
	        Assert.assertTrue("断言错误", subject.isPermitted("user:create"));
	        // 判断拥有权限：user:update and user:delete
	        Assert.assertTrue("断言错误", subject.isPermittedAll("user:update", "user:delete"));
	        // 判断没有权限：user:view
	        Assert.assertFalse("断言错误", subject.isPermitted("user:view"));
	        boolean[] result = subject.isPermitted("user:create","user:view","user:delete");
	        logger.info("--------------------------------------------------has create:{"+result[0]+"},has view:{"+result[1]+"},has delete:{"+result[2]+"}");
	        // 断言拥有权限：user:create
	        subject.checkPermission("user:create");
	        // 断言拥有权限：user:delete and user:update
	        subject.checkPermissions("user:delete", "user:update");// 简写:user:delete,update
	        // 断言拥有权限：user:view 失败抛出异常
	        subject.checkPermission("user:view");
	}

	/**
	 * @Description 自定义权限验证
	 * @param subject
	 */
	public void testCustomizeAuthorize(Subject subject){
		/**
		 * isPermitted授权认证过程：
		 * 最终调用父类AuthorizingRealm的isPermitted方法
		 * 首先获取需要验证的权限串对应的Permission实例unauthPermission
		 * 再通过子类doGetAuthorizationInfo获取当前用户的权限信息authorizationInfo
		 * 通过authorizationInfo获取当前用户Permission实例并调用implies方法与unauthPermission进行匹配
		 * */
		
		/** 授权过程演示 */
        logger.info("-----------------------Begin-------------------------");
        // isPermitted方法会调用MyRealmC的doGetAuthorizationInfo()方法
        boolean hasPermission1 = subject.isPermitted("system-edit-10"); 
        logger.info("The user has permission system-edit-10 is " + hasPermission1); // true
        logger.info("------------------------End------------------------");
        
        logger.info("-----------------------Begin-------------------------");
        // isPermitted方法会调用MyRealmC的doGetAuthorizationInfo()方法
        boolean hasPermission3 = subject.isPermitted("users-delete-1"); // false
        logger.info("The user has permission users-delete-1 is " + hasPermission3);    // true
        logger.info("------------------------End------------------------");
        
        // 判断拥有权限：user:create
		Assert.assertTrue(subject.isPermitted("user1:update"));
		Assert.assertTrue(subject.isPermitted("user2:update"));
        // 通过BitPermission方式表示的权限
		Assert.assertTrue(subject.isPermitted("+user1+2"));// 新增权限
		Assert.assertTrue(subject.isPermitted("+user1+8"));// 查看权限
		Assert.assertTrue(subject.isPermitted("+user2+10"));// 新增及查看
		Assert.assertFalse(subject.isPermitted("+user1+4"));// 没有删除权限
		// 通过MyPermisson方式表示的权限
		Assert.assertTrue(subject.isPermitted("menu-view-10"));
        // 通过MyRolePermissionResolver解析得到的权限
		Assert.assertTrue("没有该权限",subject.isPermitted("user:update"));
	}
	
}
