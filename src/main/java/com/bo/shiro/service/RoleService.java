package com.bo.shiro.service;

import com.bo.shiro.entity.Role;

/**
 * @Description 角色service
 * @author 王博
 * @version 2017年10月18日　下午1:40:46
 */
public interface RoleService {

	public Role createRole(Role role);// 创建角色
	
	public void updateRole(Role role);// 更新角色

	public void deleteRole(Long roleId);// 删除角色

	public Role findByIdentifier(String role);// 根据角色标识符查询角色
	
	// 添加角色-权限之间关系
	public void correlatePermissions(Long roleId, Long... permissionIds);

	// 移除角色-权限之间关系
	public void uncorrelatePermissions(Long roleId, Long... permissionIds);
}
