package com.bo.shiro.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bo.shiro.common.JdbcTemplateUtils;
import com.bo.shiro.entity.Permission;
import com.bo.shiro.entity.Role;
import com.bo.shiro.entity.User;
import com.bo.shiro.entity.UserStatus;
import com.bo.shiro.test.ShiroTest;

/**
 * @Description service测试
 * @author 王博
 * @version 2017年10月18日　下午5:33:55
 */
public class ServiceTest extends ShiroTest {

	protected PermissionService permissionService = new PermissionServiceImpl();
	protected RoleService roleService = new RoleServiceImpl();
	protected UserService userService = new UserServiceImpl();

	protected String password = "123";

	protected Permission p1;
	protected Permission p2;
	protected Permission p3;
	protected Permission p4;
	protected Role r1;
	protected Role r2;
	protected Role r3;
	protected User u1;
	protected User u2;
	protected User u3;
	protected User u4;

	/**
	 * @Description
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_users");
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_roles");
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_permissions");
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_users_roles");
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_roles_permissions");

		// 1、新增权限
		p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
		p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
		p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
		p4 = new Permission("menu:update", "菜单模块修改", Boolean.TRUE);
		
		permissionService.createPermission(p1);
		permissionService.createPermission(p2);
		permissionService.createPermission(p3);

		// 2、新增角色
		r1 = new Role("admin", "管理员", Boolean.TRUE);
		r2 = new Role("user", "用户", Boolean.TRUE);
		r3 = new Role("tourist", "游客", Boolean.FALSE);
		roleService.createRole(r1);
		roleService.createRole(r2);
		roleService.createRole(r3);

		// 3、关联角色-权限
		roleService.correlatePermissions(r1.getId(), p1.getId());
		roleService.correlatePermissions(r1.getId(), p2.getId());
		roleService.correlatePermissions(r1.getId(), p3.getId());
		roleService.correlatePermissions(r2.getId(), p1.getId());
		roleService.correlatePermissions(r2.getId(), p2.getId());

		// 4、新增用户
		u1 = new User("zhang", password);
		u2 = new User("li", password);
		u3 = new User("wu", password);
		u4 = new User("wang", password);
		u4.setUserStatus(UserStatus.LOCKED);
		userService.createUser(u1);
		userService.createUser(u2);
		userService.createUser(u3);
		userService.createUser(u4);
		// 5、关联用户-角色
		userService.correlateRoles(u1.getId(), r1.getId());
		userService.correlateRoles(u1.getId(), r3.getId());
	}

	@Test
	@Ignore
	public void testUserRolePermissionRelation() {
		// zhang
		Set<Role> roles = userService.findRoles(u1.getUsername());
		System.out.println(roles);
		Assert.assertEquals(2, roles.size());
		Assert.assertTrue(roles.contains(r1));

		Set<Permission> permissions = userService.findPermissions(u1.getUsername());
		System.out.println(permissions);
		Assert.assertEquals(3, permissions.size());
		Assert.assertTrue(permissions.contains(p3));

		// li
		roles = userService.findRoles(u2.getUsername());
		Assert.assertEquals(0, roles.size());
		permissions = userService.findPermissions(u2.getUsername());
		Assert.assertEquals(0, permissions.size());

		// 解除 admin - menu:update 关联
		roleService.uncorrelatePermissions(r1.getId(), p3.getId());
		permissions = userService.findPermissions(u1.getUsername());
		Assert.assertEquals(2, permissions.size());
		Assert.assertFalse(permissions.contains(p3));

		// 删除一个permission
		permissionService.deletePermission(p2.getId());
		permissions = userService.findPermissions(u1.getUsername());
		Assert.assertEquals(1, permissions.size());

		// 解除 zhang-admin关联
		userService.uncorrelateRoles(u1.getId(), r1.getId());
		roles = userService.findRoles(u1.getUsername());
		Assert.assertEquals(0, roles.size());
	}

	@Test
	@Ignore
	public void testSet(){
		Map<String, String> map1 = new HashMap<>();
		map1.put("username", "bo");
		map1.put("password", "12345");
		Map<String, String> map2 = new HashMap<>();
		map2.put("username", "lin");
		map2.put("password", "1145");
		List<Map> list = new ArrayList<>();
		list.add(map1);
		list.add(map2);
		System.out.println(list);
	}
	
}
