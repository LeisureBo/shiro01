package com.bo.shiro.dao;

import java.util.Set;

import com.bo.shiro.entity.User;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午1:59:21
 */
public interface UserDao {
	
	public User addUser(User user);

	public void updateUser(User user);

	public void deleteUser(Long userId);

	public void correlateRoles(Long userId, Long... roleIds);

	public void uncorrelateRoles(Long userId, Long... roleIds);

	User findById(Long userId);

	User findByUsername(String username);

	Set<String> findRoles(String username);

	Set<String> findPermissions(String username);
}
