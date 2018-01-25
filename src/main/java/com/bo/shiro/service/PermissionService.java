package com.bo.shiro.service;

import com.bo.shiro.entity.Permission;

/**
 * @Description 权限service
 * @author 王博
 * @version 2017年10月18日　上午10:40:46
 */
public interface PermissionService {
	
	public Permission createPermission(Permission permission);// 创建权限

	public void updatePermission(Permission permission);// 更新权限
	
	public void deletePermission(Long permissionId);// 删除权限
	
	public Permission findByIdentifier(String permission);// 根据权限标识符查找权限
}
