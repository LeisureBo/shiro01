package com.bo.shiro.web.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月22日　上午11:40:09
 */
public class PermissionControllerFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) throws IOException {
		return super.onAccessDenied(arg0, arg1);
	}

}
