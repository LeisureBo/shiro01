package com.bo.shiro.authz.permission;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.Permission;

/**
 * @Description 实现位移方式的权限，如规则是：权限字符串格式：+资源字符串+权限位+实例ID；以"+"开头中间通过"+"分割； 
 * 				权限：0 表示所有权限；1 新增（二进制：0001）；2 修改（二进制：0010）；4 删除（二进制：0100）；8 查看（二进制：1000）；
 * 				如 +user+3 表示对资源user拥有新增/修改权限；+user+10 表示对资源user拥有修改/查看权限
 * @author 王博
 * @version 2017年10月13日　下午4:01:31
 */
public class BitPermission implements Permission, Serializable {

	private static final long serialVersionUID = 6185409392921006821L;
	private String resourceNaming;// 资源标识串
	private int permissionBit;// 权限二进制位
	private String instanceId;// 实例ID

	public BitPermission(String permissionString) {
		String[] array = permissionString.split("\\+");
		if (array.length > 1) {
			resourceNaming = array[1];
		}
		if (StringUtils.isEmpty(resourceNaming)) {
			resourceNaming = "*";
		}
		if (array.length > 2) {
			permissionBit = Integer.valueOf(array[2]);
		}
		if (array.length > 3) {
			instanceId = array[3];
		}
		if (StringUtils.isEmpty(instanceId)) {
			instanceId = "*";
		}
	}

	@Override
	public boolean implies(Permission p) {
		if (!(p instanceof BitPermission)) {
			return false;
		}
		BitPermission other = (BitPermission) p;
		if (!("*".equals(this.resourceNaming) || this.resourceNaming.equals(other.resourceNaming))) {
			return false;
		}
		if (!(this.permissionBit == 0 || (this.permissionBit & other.permissionBit) != 0)) {
			return false;
		}
		if (!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
			return false;
		}
		return true;
	}

}
