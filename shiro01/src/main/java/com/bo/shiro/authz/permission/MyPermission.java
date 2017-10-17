package com.bo.shiro.authz.permission;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.shiro.authz.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description 自定义Permission
 * @author 王博
 * @version 2017年9月20日　下午9:06:05
 */
public class MyPermission implements Permission, Serializable {

	private static final Logger logger = LoggerFactory.getLogger(MyPermission.class);
	private static final long serialVersionUID = 4936382291779021987L;
	
	protected static final String WILDCARD_TOKEN = "*";// 通配符
	protected static final String PART_DIVIDER_TOKEN = "-";// 权限串分割符  \\+
	protected static final boolean DEFAULT_CASE_SENSITIVE = false;// 区分大小写
	
	/** 规则："资源标识符:操作:对象实例ID" 即对哪个资源的哪个实例可以进行什么操作。*/
	
    private String domain = "";
    private String action = "";
    private String instanceId = "";
	
	public MyPermission() {
	}

	public MyPermission(String permissionString){
		this(permissionString, DEFAULT_CASE_SENSITIVE);
	}
	
	public MyPermission(String permissionString, boolean caseSensitive){
		logger.info("The constructor MyPermission(..) was invoke.");
		setParts(permissionString, caseSensitive);
	}
	
	private void setParts(String permissionString, boolean caseSensitive){
		if(StringUtils.isBlank(permissionString)){
			return;
		}
		if(caseSensitive){
			permissionString = permissionString.toLowerCase();
		}
		String[] parts = permissionString.split(PART_DIVIDER_TOKEN);
		if(parts != null && parts.length == 3){
			this.setDomain(parts[0]);
			this.setAction(parts[1]);
			this.setInstanceId(parts[2]);
		}
	}
	
	/**
	 * 当前调用implies的Permission对象的权限是否大于或等于该参数
	 */
	@Override
	public boolean implies(Permission p) {
		logger.info("The method implies(..) was invoke.");
		if(!(p instanceof MyPermission)){
			return false;
		}
		MyPermission that = (MyPermission) p;
		if(this.domain.equals(that.domain) && this.action.equals(that.action) 
				&& this.instanceId.equals(that.instanceId)){
			return true;
		}
		return false;
	}

	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);// com.bo.shiro.permission.MyPermission@10811b5[domain=user,action=update,instanceId=1]
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
}
