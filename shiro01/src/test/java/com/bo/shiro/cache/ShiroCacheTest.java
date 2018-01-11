package com.bo.shiro.cache;

import static org.junit.Assert.fail;
import net.sf.ehcache.CacheManager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import com.bo.shiro.entity.Permission;
import com.bo.shiro.entity.Role;
import com.bo.shiro.realm.UserRealm;
import com.bo.shiro.service.ServiceTest;

/**
 * @Description 
 * @author 王博
 * @version 2018年1月2日　上午11:42:29
 */
public class ShiroCacheTest extends ServiceTest{
	
	/**
	 * 注：因为ehcache在2.5以后，CacheManager使用了Singleton，
	 * 本测试用例在创建多个CacheManager时就会产生错误，需将ehcache-core版本号改为2.4.8
	 */
	
	private String configFile = "classpath:shiro/cache/shiro_cache.ini";
	
	@Override
	public void setUp() throws Exception {
		u1 = userService.findByUsername("zhang");
		r2 = roleService.findByIdentifier("user");
	}

	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		userService.changePassword(u1.getId(), password);
        getUserRealm().clearCache(subject().getPrincipals());
        super.tearDown();
	}

	@Test
	@Ignore
	public void testClearCachedAuthenticationInfo() {
		// 首先登录成功(此时会缓存相应的AuthenticationInfo)
		login(configFile, u1.getUsername(), password);
		// 然后修改密码；此时密码就变了
		userService.changePassword(u1.getId(), password+"bo");
		// 接着需要清空缓存的AuthenticationInfo, 否则下次登录时会获取到上传缓存的AuthenticationInfo
		getUserRealm().clearCachedAuthenticationInfo(subject().getPrincipals());
		login(configFile, u1.getUsername(), password+"bo");
	}
	
	@Test
	public void testClearCachedAuthorizationInfo(){
		login(configFile, u1.getUsername(), password);
		userService.uncorrelateRoles(u1.getId(), r2.getId());
		subject().checkRole("admin");// 首次授权认证即会缓存授权信息
		userService.correlateRoles(u1.getId(), r2.getId());
		getUserRealm().clearCachedAuthorizationInfo(subject().getPrincipals());
		// 如果不清理缓存此处授权验证会失败
		subject().checkRole(r2.getRole());
	}
	
	private UserRealm getUserRealm(){
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
	    UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
	    return userRealm;
	}
	
}
