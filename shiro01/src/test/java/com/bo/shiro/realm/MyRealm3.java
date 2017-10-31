package com.bo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

import com.bo.shiro.entity.User;

/**
 * @Description
 * @author 王博
 * @version 2017年10月30日　下午1:22:55
 */
public class MyRealm3 implements Realm {

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
		User user = new User("bo", "123");
		return new SimpleAuthenticationInfo(user, // 身份 User类型
				"123", // 凭据
				getName() // Realm Name
		);
	}

}
