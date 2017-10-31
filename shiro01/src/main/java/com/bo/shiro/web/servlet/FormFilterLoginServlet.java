package com.bo.shiro.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月22日　下午4:00:11
 */
@WebServlet("/formfilterlogin")
public class FormFilterLoginServlet extends HttpServlet {

	private static final long serialVersionUID = -419798302926757883L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 在登录Servlet中通过shiroLoginFailure得到authc登录失败时的异常类型名，然后根据此异常名来决定显示什么错误消息。
		String errorClassName = (String) req.getAttribute("shiroLoginFailure");//shiroLoginFailure
//		System.out.println(errorClassName);
		if(UnknownAccountException.class.getName().equals(errorClassName)){//org.apache.shiro.authc.UnknownAccountException
			req.setAttribute("msg", "用户名或密码错误!");
		}else if(IncorrectCredentialsException.class.getName().equals(errorClassName)){
			req.setAttribute("msg", "用户名或密码错误!");
		}else if(errorClassName != null){
			req.setAttribute("msg", "未知错误: " + errorClassName);
		}
		req.getRequestDispatcher("/shiro/user/formfilterlogin.jsp").forward(req, resp);
	}
	
}
