package com.bo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

/**
 * @Description 
 * @author 王博
 * @version 2017年10月30日　下午1:19:47
 */
public class MyRealm1 implements Realm {

	/**
	 * @Description 
	 * @return
	 */
	@Override
	public String getName() {
		return this.getClass().getName();
	}

	/**
	 * @Description 
	 * @param token
	 * @return
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	/**
	 * @Description 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		return new SimpleAuthenticationInfo(
                "bo", //身份 字符串类型
                "123",   //凭据
                getName() //Realm Name
        );
	}

}
