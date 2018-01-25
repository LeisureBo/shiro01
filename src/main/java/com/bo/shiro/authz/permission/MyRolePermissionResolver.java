package com.bo.shiro.authz.permission;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * @Description 用于根据角色字符串来解析得到权限集合
 * @author 王博
 * @version 2017年10月13日　下午4:25:50
 */
public class MyRolePermissionResolver implements RolePermissionResolver {

	@Override
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		if(roleString.equals("role1")){
			return Arrays.asList((Permission)new WildcardPermission("user:create"),(Permission)new WildcardPermission("user:update"));
		}else if(roleString.equals("role2")){
			return Arrays.asList((Permission)new WildcardPermission("user:create"),(Permission)new WildcardPermission("user:delete"));
		}else if(roleString.equals("role3")){
			return Arrays.asList((Permission)new WildcardPermission("user:view"));
		}
		return null;
	}

}
