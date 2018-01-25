package com.bo.shiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bo.shiro.common.JdbcTemplateUtils;
import com.bo.shiro.entity.Permission;
import com.bo.shiro.entity.Role;
import com.bo.shiro.entity.User;
import com.bo.shiro.entity.UserStatus;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午2:16:40
 */
public class UserDaoImpl implements UserDao, RowMapper<User> {

	private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();

	@Override
	public User addUser(final User user) {
		final String sql = "insert into sys_users(username, password, salt, user_status) values(?,?,?,?)";
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
		String sql = "update sys_users set username=?, password=?, salt=?, user_status=? where id=?";
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
		try {
			return jdbcTemplate.queryForObject(sql, this, userId);
		} catch (DataAccessException e) {
			return null;
		}
	}

	@Override
	public User findByUsername(String username) {
		String sql = "select * from sys_users where username=?";
		try {
			return jdbcTemplate.queryForObject(sql, this, username);
		} catch (DataAccessException e) {
			return null;
		}
	}

	@Override
	public Set<Role> findRoles(String username) {
		String sql = "select r.* from sys_users u, sys_roles r,sys_users_roles ur where u.username=? and u.id=ur.user_id and r.id=ur.role_id";
		try {
			return new HashSet(jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class), username));
		} catch (DataAccessException e) {
			return null;
		}
	}

	@Override
	public Set<Permission> findPermissions(String username) {
		String sql = "select p.* from sys_permissions p INNER JOIN sys_roles_permissions rp ON p.id=rp.permission_id where role_id in ("
				+ "select ur.role_id from sys_users u INNER JOIN sys_users_roles ur ON u.id=ur.user_id where username=?);";
		try {
			return new HashSet(jdbcTemplate.query(sql, new BeanPropertyRowMapper(Permission.class), username));
		} catch (DataAccessException e) {
			return null;
		}
	}

	/**
	 * @Description
	 * @param rs
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setSalt(rs.getString("salt"));
		user.setUserStatus(UserStatus.getByOrdinal(rs.getInt("user_status")));
		return user;
	}

}
