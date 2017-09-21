package com.bo.shiro.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月21日　上午10:30:55
 */
public class ShiroUtils {
	
	public static Subject login(String iniPath, String username, String password){
		// 读取配置文件，初始化SecurityManager工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniPath);
		// 获取SecurityManager实例
		SecurityManager securityManager = factory.getInstance();
		// 把securityManager 实例绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		// 得到当前用户
		Subject currentUser = SecurityUtils.getSubject();
		// 创建token令牌，用户名/密码
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			// 登陆
			currentUser.login(token);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fail");
		}
		return currentUser;
	}
	
}
