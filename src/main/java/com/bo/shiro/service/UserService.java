package com.bo.shiro.service;

import java.util.Set;

import com.bo.shiro.entity.Permission;
import com.bo.shiro.entity.Role;
import com.bo.shiro.entity.User;

/**
 * @Description 用户service
 * @author 王博
 * @version 2017年10月18日　下午1:41:31
 */
public interface UserService {
	
	public User createUser(User user); // 创建账户

	public void changePassword(Long userId, String newPassword);// 修改密码

	public void correlateRoles(Long userId, Long... roleIds); // 添加用户-角色关系

	public void uncorrelateRoles(Long userId, Long... roleIds);// 移除用户-角色关系

	public User findByUsername(String username);// 根据用户名查找用户

	public Set<Role> findRoles(String username);// 根据用户名查找其角色

	public Set<Permission> findPermissions(String username); // 根据用户名查找其权限
}
