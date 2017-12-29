package com.bo.shiro.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月22日　下午5:38:23
 */
@WebServlet("/mylogout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = -2149511683446579930L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Session session = SecurityUtils.getSubject().getSession();
		System.out.println(session.getClass().getName());
		System.out.println("session id:"+session.getId());
		System.out.println("session host:"+session.getHost());
		System.out.println("session timeout:"+session.getTimeout());
		System.out.println("session startTime:"+session.getStartTimestamp());
		System.out.println("session lastAccessTime:"+session.getLastAccessTime());
		System.out.println("session attribute[skey]:"+session.getAttribute("skey"));
		SecurityUtils.getSubject().logout();
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
}
