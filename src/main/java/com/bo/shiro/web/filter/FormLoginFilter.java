package com.bo.shiro.web.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * @Description 基于表单登录拦截器
 * @author 王博
 * @version 2017年10月30日　下午7:16:17
 */
public class FormLoginFilter extends PathMatchingFilter {

	/**onPreHandle主要流程：
	 * 1、首先判断是否已经登录过了，如果已经登录过了继续拦截器链即可；
	 * 2、如果没有登录，看看是否是登录请求，如果是get方法的登录页面请求，则继续拦截器链（到请求页面），否则如果是get方法的其他页面请求则保存当前请求并重定向到登录页面；
	 * 3、如果是post方法的登录页面表单提交请求，则收集用户名/密码登录即可，如果失败了保存错误消息到“shiroLoginFailure”并返回到登录页面；
	 * 4、如果登录成功了，且之前有保存的请求，则重定向到之前的这个请求，否则到默认的成功页面。
	 */

	private String loginUrl = "/static/login.jsp";
	private String successUrl = "/";

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// 判断是否已登录
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return true;// 继续执行过滤器链
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 判断是否是登录请求
		if (isLoginRequest(req)) {
			// 判断POST请求
			if ("post".equalsIgnoreCase(req.getMethod())) {
				// 登录成功
				if (login(req)) {
					return redirectToSuccessUrl(req, resp);
				}
			}
			return true;// 继续过滤器链
		} else {
			// 保存当前地址并重定向到登录界面
			saveRequestAndRedirectToLogin(req, resp);
			return false;
		}
	}

	private boolean isLoginRequest(HttpServletRequest request) {
//		System.out.println(WebUtils.getPathWithinApplication(request));
		return pathsMatch(loginUrl, request);
	}

	private boolean login(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
		} catch (Exception e) {
			request.setAttribute("shiroLoginFailure", e.getClass());
			return false;
		}
		return true;
	}

	private boolean redirectToSuccessUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebUtils.redirectToSavedRequest(request, response, successUrl);// 如果没有保存的请求则默认跳转到successUrl
		return false;
	}

	private void saveRequestAndRedirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebUtils.saveRequest(request);
		WebUtils.issueRedirect(request, response, loginUrl);
	}
}
