package com.bo.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * @Description 任意角色授权拦截器:Shiro提供roles拦截器，其验证用户拥有所有角色，但没有提供验证用户拥有任意角色的拦截器。
 * @author 王博
 * @version 2017年10月31日　上午11:17:58
 */
public class AnyRolesFilter extends AccessControlFilter {

	private String unauthorizedUrl = "/shiro/user/unauthorized.jsp";
	private String loginUrl = "/login.jsp";

	/**
	 * @Description 过滤哪些角色可以访问
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		String[] roles = (String[]) mappedValue;
		if (roles == null) {
			return true;// 如果没有设置角色参数，默认成功
		}
		for (String role : roles) {
			if (getSubject(request, response).hasRole(role)) {
				return true;// 拥有访问权限的角色
			}
		}
		return false;// 跳到onAccessDenied处理
	}

	/**
	 * @Description 访问被拒绝时的处理
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if(!subject.isAuthenticated()){// 表示没有登录，重定向到登录页面
			saveRequest(request);
			WebUtils.issueRedirect(request, response, loginUrl);
		}else {
			if(StringUtils.isNotBlank(unauthorizedUrl)){// 如果有未授权页面跳转过去
				WebUtils.issueRedirect(request, response, unauthorizedUrl);
			}else {// 否则返回401未授权状态码
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);  
			}
			
		}
		return false;
	}

}
