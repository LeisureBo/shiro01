package com.bo.shiro.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

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
		SecurityUtils.getSubject().logout();
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
}
