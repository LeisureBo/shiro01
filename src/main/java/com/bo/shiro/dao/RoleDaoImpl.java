package com.bo.shiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.bo.shiro.common.JdbcTemplateUtils;
import com.bo.shiro.entity.Role;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午4:48:27
 */
public class RoleDaoImpl implements RoleDao, RowMapper<Role> {

	private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();

	@Override
	public Role addRole(final Role role) {
		final String sql = "insert into sys_roles(role, description, available) values(?,?,?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement pstat = connection.prepareStatement(sql, new String[] { "id" });
				pstat.setString(1, role.getRole());
				pstat.setString(2, role.getDescription());
				pstat.setBoolean(3, role.getAvailable());
				return pstat;
			}
		}, keyHolder);
		role.setId(keyHolder.getKey().longValue());
		return role;
	}

	@Override
	public void updateRole(Role role) {
		String sql = "update sys_roles set role=?, description=?, available=? where id=?";
		jdbcTemplate.update(sql, role.getRole(), role.getDescription(), role.getAvailable(), role.getId());
	}

	@Override
	public void deleteRole(Long roleId) {
		// 首先把和role关联的相关表数据删掉
		String sql = "delete from sys_users_roles where role_id=?";
		jdbcTemplate.update(sql, roleId);

		sql = "delete from sys_roles where id=?";
		jdbcTemplate.update(sql, roleId);
	}

	@Override
	public Role findByIdentifier(String role) {
		String sql = "select * from sys_roles where role=?";
		try {
			return jdbcTemplate.queryForObject(sql, this, role);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	@Override
	public void correlatePermissions(Long roleId, Long... permissionIds) {
		if (permissionIds == null || permissionIds.length == 0) {
			return;
		}
		String sql = "insert into sys_roles_permissions(role_id, permission_id) values(?,?)";
		for (Long permissionId : permissionIds) {
			if (!exists(roleId, permissionId)) {
				jdbcTemplate.update(sql, roleId, permissionId);
			}
		}
	}

	@Override
	public void uncorrelatePermissions(Long roleId, Long... permissionIds) {
		if (permissionIds == null || permissionIds.length == 0) {
			return;
		}
		String sql = "delete from sys_roles_permissions where role_id=? and permission_id=?";
		for (Long permissionId : permissionIds) {
			if (exists(roleId, permissionId)) {
				jdbcTemplate.update(sql, roleId, permissionId);
			}
		}
	}

	private boolean exists(Long roleId, Long permissionId) {
		String sql = "select count(1) from sys_roles_permissions where role_id=? and permission_id=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, roleId, permissionId) != 0;
	}

	/**
	 * @Description 
	 * @param rs
	 * @param rowNum
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		Role role = new Role();
		role.setId(rs.getLong("id"));
		role.setRole(rs.getString("role"));
		role.setDescription(rs.getString("description"));
		role.setAvailable(rs.getBoolean("available"));
		return role;
	}

}
