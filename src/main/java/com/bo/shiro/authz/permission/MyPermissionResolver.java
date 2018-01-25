package com.bo.shiro.authz.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description permission生成器
 * @author 王博
 * @version 2017年9月20日　下午9:43:13
 */
public class MyPermissionResolver implements PermissionResolver {

	private static final Logger logger = LoggerFactory.getLogger(MyPermissionResolver.class);

	public MyPermissionResolver() {
	}

	@Override
	public Permission resolvePermission(String permissionString) {
		logger.info("The method resolvePermission({}) was invoke.", permissionString);
		if(permissionString.indexOf(MyPermission.PART_DIVIDER_TOKEN) != -1){
			return new MyPermission(permissionString);
		}else if(permissionString.startsWith("+")){
			return new BitPermission(permissionString);
		}
		return new WildcardPermission(permissionString);
	}

}
