package com.bo.shiro.service;

import java.util.Set;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bo.shiro.common.PasswordHelper;
import com.bo.shiro.dao.UserDao;
import com.bo.shiro.dao.UserDaoImpl;
import com.bo.shiro.entity.Permission;
import com.bo.shiro.entity.Role;
import com.bo.shiro.entity.User;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午5:10:46
 */
public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();
	private PasswordHelper passwordHelper = new PasswordHelper();

	/**
	 * 创建用户
	 * 
	 * @param user
	 */
	public User createUser(User user) {
		// 加密密码
		passwordHelper.encryptPassword(user);
		return userDao.addUser(user);
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.findById(userId);
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		userDao.updateUser(user);
	}

	/**
	 * 添加用户-角色关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void correlateRoles(Long userId, Long... roleIds) {
		userDao.correlateRoles(userId, roleIds);
	}

	/**
	 * 移除用户-角色关系
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void uncorrelateRoles(Long userId, Long... roleIds) {
		userDao.uncorrelateRoles(userId, roleIds);
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<Role> findRoles(String username) {
		return userDao.findRoles(username);
	}

	/**
	 * 根据用户名查找其权限
	 * 
	 * @param username
	 * @return
	 */
	public Set<Permission> findPermissions(String username) {
		return userDao.findPermissions(username);
	}

}
