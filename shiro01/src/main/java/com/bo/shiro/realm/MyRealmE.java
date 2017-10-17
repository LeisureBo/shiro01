package com.bo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @Description HashedCredentialsMatcher实现密码验证服务Realm
 * @author 王博
 * @version 2017年10月16日　下午1:38:22
 */
public class MyRealmE extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//		String username = String.valueOf(token.getPrincipal());// 用户名
//		String password = new String((char[])token.getCredentials());// 密码
//		String salt = username + new SecureRandomNumberGenerator().nextBytes().toHex();// 盐是用户名+随机数
		
		String username = String.valueOf(token.getPrincipal());
//		if(!username.equals("bo")){
			// 此处不要抛出异常 否则会在ModularRealmAuthenticator进行验证时即在getAuthenticationInfo(它会调用当前方法)时向外层抛出异常,
			// 而被AbstractAuthenticator在authenticate方法内捕获,从而不会执行credentialsMatcher的密码验证即doCredentialsMatch()
			// 此处如果用户不存在直接返回null, 这会在ModularRealmAuthenticator内部可能抛出UnknownAccountException异常(单realm)
//			throw new UnknownAccountException("No account found for user["+username+"]");
//		}
		String password = "766dd5d211808bd24ada92a0e3bea389";// 12345(此处密码固定由用户名"bo"+salt混合盐生成)
		String salt = username + "40ed204cdb8915729112cecaf3dd1f4c";// username+随机数16进制
		
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(salt));// 设置盐, HashedCredentialsMatcher会自动识别这个盐
		return authenticationInfo;
	}

}
