package com.bo.shiro.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @Description 角色实体
 * @author 王博
 * @version 2017年10月18日　上午11:03:13
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 7656123417703257549L;

	private Long id;
	private String role;// 角色标识符,用于后台判断
	private String description;// 角色描述,用于UI显示
	private Boolean available = Boolean.FALSE;// 角色是否可用,如果不可用将不会添加给用户
	
	public Role(){
		
	}
	
	public Role(String role, String description, Boolean available){
		this.role = role;
		this.description = description;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
		Role other = (Role) obj;
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
