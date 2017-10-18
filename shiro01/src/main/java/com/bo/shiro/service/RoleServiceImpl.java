package com.bo.shiro.service;

import com.bo.shiro.dao.RoleDao;
import com.bo.shiro.dao.RoleDaoImpl;
import com.bo.shiro.entity.Role;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午5:18:20
 */
public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao = new RoleDaoImpl();

	/**
	 * 创建角色
	 * @param role
	 */
	public Role createRole(Role role) {
		return roleDao.addRole(role);
	}

	/**
	 * 更新角色 
	 * @param role
	 */
	public void updateRole(Role role) {
		roleDao.updateRole(role);
	}

	/**
	 * 删除角色
	 * @param roleId
	 */
	public void deleteRole(Long roleId) {
		roleDao.deleteRole(roleId);
	}

	/**
	 * 添加角色-权限之间关系
	 * @param roleId
	 * @param permissionIds
	 */
	public void correlatePermissions(Long roleId, Long... permissionIds) {
		roleDao.correlatePermissions(roleId, permissionIds);
	}

	/**
	 * 移除角色-权限之间关系
	 * @param roleId
	 * @param permissionIds
	 */
	public void uncorrelatePermissions(Long roleId, Long... permissionIds) {
		roleDao.uncorrelatePermissions(roleId, permissionIds);
	}

}
