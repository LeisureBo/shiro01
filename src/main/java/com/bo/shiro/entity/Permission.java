package com.bo.shiro.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @Description 权限实体
 * @author 王博
 * @version 2017年10月18日　上午11:07:52
 */
public class Permission implements Serializable {

	private static final long serialVersionUID = -3308955889741991499L;

	private Long id;
	private String permission;// 权限标识符,程序中判断使用
	private String description;// 权限描述,UI界面显示使用
	private Boolean available = Boolean.FALSE;// 是否可用,如果不可用将不会添加给用户

	public Permission() {
	}

	public Permission(String permission, String description, Boolean available) {
		this.permission = permission;
		this.description = description;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
