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
import com.bo.shiro.entity.Permission;

/**
 * @Description 
 * @author 王博
 * @version 2017年10月18日　下午5:03:02
 */
public class PermissionDaoImpl implements PermissionDao, RowMapper<Permission> {

	private JdbcTemplate jdbcTemplate = JdbcTemplateUtils.jdbcTemplate();
	
	@Override
	public Permission addPermission(final Permission permission) {
		final String sql = "insert into sys_permissions(permission, description, available) values(?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement pstat = connection.prepareStatement(sql, new String[] {"id"});
                pstat.setString(1, permission.getPermission());
                pstat.setString(2, permission.getDescription());
                pstat.setBoolean(3, permission.getAvailable());
                return pstat;
            }
        }, keyHolder);
        permission.setId(keyHolder.getKey().longValue());
        return permission;
	}

	@Override
	public void updatePermission(Permission permission) {
		String sql = "update sys_permissions set permission=?, description=?, available=? where id=?";
		jdbcTemplate.update(sql, permission.getPermission(), permission.getDescription(), permission.getAvailable(), permission.getId());
	}

	@Override
	public void deletePermission(Long permissionId) {
		//首先把与permission关联的相关表的数据删掉
        String sql = "delete from sys_roles_permissions where permission_id=?";
        jdbcTemplate.update(sql, permissionId);

        sql = "delete from sys_permissions where id=?";
        jdbcTemplate.update(sql, permissionId);
	}

	@Override
	public Permission findByIdentifier(String permission) {
		String sql = "select * from sys_permissions where permission=?";
		try {
			return jdbcTemplate.queryForObject(sql, this, permission);
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
	public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
		Permission permission = new Permission();
		permission.setId(rs.getLong("id"));
		permission.setPermission(rs.getString("permission"));
		permission.setDescription(rs.getString("description"));
		permission.setAvailable(rs.getBoolean("available"));
		return permission;
	}

}
