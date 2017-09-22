package com.bo.shiro.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月21日　下午7:46:44
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = -666080993445743358L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/index.jsp").forward(req, resp);// doGet请求直接跳到登录页
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			req.setAttribute("msg", "用户名或密码不能为空!");
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
			return;
		}
		// 加密的密码
		UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
		Subject subject = SecurityUtils.getSubject();
		String msg = "";
		try {
			// 身份验证
			subject.login(token);
		} catch (UnknownAccountException e){
			msg = "用户名或密码错误!";
		} catch (IncorrectCredentialsException e){
			msg = "用户名或密码错误!";
		} catch (AuthenticationException e) {
			//其他错误，比如锁定，如果想单独处理请单独catch 处理
			msg = "其他错误" + e.getMessage();
		}
		
		if(subject.isAuthenticated() && msg.equals("")){// 登录成功返回欢迎界面
			req.setAttribute("subject", subject);
			String jumpUrl = "shiro/user/welcome.jsp";
			String fromUrl = (String)req.getSession().getAttribute("fromUrl");
			// 如果用户访问资源时被拦截到登录,则登录认证后重定向到该地址
			if(!StringUtils.isBlank(fromUrl)){
				jumpUrl = fromUrl;
				req.getSession().removeAttribute("fromUrl");
			}
//			resp.sendRedirect(jumpUrl);// servlet重定向不加"/"前置路径为工程名
			req.getRequestDispatcher("/"+jumpUrl).forward(req, resp);
		} else {// 登录失败返回首页
			req.setAttribute("msg", msg);
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
		
	}
	
	private String getFromUrl(HttpServletRequest req){
		String fromUrl = req.getRequestURI();// 获取请求资源路径(不含参数)
		String queryString = req.getQueryString();// 获取get请求的参数串
		if(!StringUtils.isBlank(queryString)){
			fromUrl += "?" + queryString;
		}
		req.getSession().setAttribute("fromUrl", fromUrl);
		
		/*Enumeration<String> paramNames = req.getParameterNames();
		boolean flag = false;
		StringBuffer paramStr = new StringBuffer("?");
		while(paramNames != null && paramNames.hasMoreElements()){
			if(!flag) flag = true;
			String name = paramNames.nextElement();
			String value = req.getParameter(name);
			paramStr.append(name).append("=").append(value);
			if(paramNames.hasMoreElements()){
				paramStr.append("&");
			}
		}*/
		return fromUrl;
	}
	
}
