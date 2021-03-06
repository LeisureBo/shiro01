package com.bo.shiro.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm.SaltStyle;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月20日　下午9:04:18
 */
public class ShiroTest {

	protected static Logger logger;
	
	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger = LoggerFactory.getLogger(ShiroTest.class);
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
		ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}

	protected void login(String configFile, String username, String password){
		//1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
                new IniSecurityManagerFactory(configFile);

        //2、得到SecurityManager实例
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        /** shiro ini配置文件中不支持Enum枚举属性注入 需要在securityManager实例绑定前进行设置 */
        Collection<Realm> realms = ((RealmSecurityManager)securityManager).getRealms();
        for(Realm realm : realms){
        	if(realm instanceof JdbcRealm){
        		JdbcRealm jdbcRealm = (JdbcRealm) realm;
        		// 表示使用密码+盐的机制返回用户信息 @see org.apache.shiro.realm.jdbc.JdbcRealm:doGetAuthenticationInfo()
        		jdbcRealm.setSaltStyle(SaltStyle.COLUMN);
        	}
        }
        //3、SecurityManager实例绑定到SecurityUtils(这是一个全局设置,设置一次即可)
        SecurityUtils.setSecurityManager(securityManager);

        //4、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        subject.login(token);
	}
	
	public Subject subject(){
		return SecurityUtils.getSubject();
	}
	
	@Test
	@Ignore
	public void test() {
		String permission = "system-edit-10";
		String[] parts = permission.split("\\-");
		for(String part : parts){
			System.out.println(part);
		}
		String password = "12345";
		System.out.println(DigestUtils.md5Hex(password));
		
		System.out.println(UnknownAccountException.class.getName());
	}
	
}
