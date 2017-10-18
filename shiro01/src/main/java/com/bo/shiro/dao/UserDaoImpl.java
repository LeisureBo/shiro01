package com.bo.shiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bo.shiro.common.JdbcTemplateUtils;
import com.bo.shiro.entity.User;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午2:16:40
 */
public class UserDaoImpl implements UserDao {

	private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();

	@Override
	public User addUser(final User user) {
		final String sql = "insert into sys_users(username, password, salt, userstatus) values(?,?,?,?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstat = con.prepareStatement(sql, new String[] { "id" });// 后面一个参数表示需要返回的列
				pstat.setString(1, user.getUsername());
				pstat.setString(2, user.getPassword());
				pstat.setString(3, user.getSalt());
				pstat.setInt(4, user.getUserStatus().ordinal());
				return pstat;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().longValue());// 设置返回的主键
		return user;
	}

	@Override
	public void updateUser(User user) {
		String sql = "update sys_users set username=?, password=?, salt=?, userstatus=? where id=?";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getSalt(), user.getUserStatus().ordinal(), user.getId());
	}

	@Override
	public void deleteUser(Long userId) {
		String sql = "delete from sys_users where id=?";
		jdbcTemplate.update(sql, userId);
	}

	@Override
	public void correlateRoles(Long userId, Long... roleIds) {
		if (roleIds == null || roleIds.length == 0) {
			return;
		}
		String sql = "insert into sys_users_roles(user_id, role_id) values(?,?)";
		for (Long roleId : roleIds) {
			if (!exists(userId, roleId)) {
				jdbcTemplate.update(sql, userId, roleId);
			}
		}
	}

	@Override
	public void uncorrelateRoles(Long userId, Long... roleIds) {
		if (roleIds == null || roleIds.length == 0) {
			return;
		}
		String sql = "delete from sys_users_roles where user_id=? and role_id=?";
		for (Long roleId : roleIds) {
			if (exists(userId, roleId)) {
				jdbcTemplate.update(sql, userId, roleId);
			}
		}
	}

	private boolean exists(Long userId, Long roleId) {
		String sql = "select count(1) from sys_users_roles where user_id=? and role_id=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, userId, roleId) != 0;
	}

	@Override
	public User findById(Long userId) {
		String sql = "select * from sys_users where id=?";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), userId);
	}

	@Override
	public User findByUsername(String username) {
		String sql = "select * from sys_users where username=?";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), username);
	}

	@Override
	public Set<String> findRoles(String username) {
		String sql = "select role from sys_users u, sys_roles r,sys_users_roles ur where u.username=? and u.id=ur.user_id and r.id=ur.role_id";
		return new HashSet(jdbcTemplate.queryForList(sql, String.class, username));
	}

	@Override
	public Set<String> findPermissions(String username) {
		String sql = "select permission from sys_users u, sys_roles r, sys_permissions p, sys_users_roles ur, sys_roles_permissions rp where u.username=? and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and p.id=rp.permission_id";
		return new HashSet(jdbcTemplate.queryForList(sql, String.class, username));
	}

}
