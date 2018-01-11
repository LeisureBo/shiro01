package com.bo.shiro.dao;

import com.bo.shiro.entity.Role;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午2:01:11
 */
public interface RoleDao {
	
	public Role addRole(Role role);
	
	public void updateRole(Role role);

	public void deleteRole(Long roleId);

	public Role findByIdentifier(String role);
	
	public void correlatePermissions(Long roleId, Long... permissionIds);

	public void uncorrelatePermissions(Long roleId, Long... permissionIds);
}
