package com.bo.shiro.dao;

import com.bo.shiro.entity.Permission;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午2:02:36
 */
public interface PermissionDao {
	
	public Permission addPermission(Permission permission);

	public void updatePermission(Permission permission);
	
	public void deletePermission(Long permissionId);
}
