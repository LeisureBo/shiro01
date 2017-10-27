package com.bo.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.bo.shiro.entity.Permission;
import com.bo.shiro.entity.Role;
import com.bo.shiro.entity.User;
import com.bo.shiro.entity.UserStatus;
import com.bo.shiro.service.UserService;
import com.bo.shiro.service.UserServiceImpl;

/**
 * @Description 
 * @author 王博
 * @version 2017年10月19日　下午5:45:01
 */
public class UserRealm extends AuthorizingRealm {

	private UserService userService = new UserServiceImpl();
	
	/**
	 * @Description 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Set<Role> roles = userService.findRoles(username);
		authorizationInfo.setRoles(convertToStrs(userService.findRoles(username)));
		authorizationInfo.setStringPermissions(convertToStrs(userService.findPermissions(username)));
		return authorizationInfo;
	}

	/**
	 * @Description 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		User user = userService.findByUsername(username);//EmptyResultDataAccessException
		if(user == null){
			throw new UnknownAccountException();// 账户不存在
		}
		if(user.getUserStatus() == UserStatus.LOCKED){
			throw new LockedAccountException();// 账户被锁定
		}
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user.getUsername(),// 用户名
				user.getPassword(),// 密码
				ByteSource.Util.bytes(user.getCredentialsSalt()),// salt=username+salt
				getName());
		return authenticationInfo;
	}

	private Set<String> convertToStrs(Set origins){
		Set<String> set = new HashSet<>();
		if(origins != null && !origins.isEmpty()){
			for(Object obj : origins){
				if(obj instanceof Role){
					Role role = (Role) obj;
					set.add(role.getRole());
				}else if(obj instanceof Permission){
					Permission permission = (Permission) obj;
					set.add(permission.getPermission());
				}else {
					break;
				}
			}
		}
		return set;
	}
	
}
