package com.bo.shiro.entity;

/**
 * @Description 用户状态
 * @author 王博
 * @version 2017年10月18日　上午10:46:51
 */
public enum UserStatus {
	// 状态序号 0,1,...
	NORMAL("正常"), LOCKED("被锁定");
	
	private String detail;
	
	private UserStatus(String detail){
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
