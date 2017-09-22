package com.bo.shiro.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月21日　下午10:06:18
 */
@WebServlet("/unauthorized")
public class UnAuthorizedServlet extends HttpServlet {

	private static final long serialVersionUID = 2375711066056636472L;

	public UnAuthorizedServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/shiro/user/unauthorized.jsp").forward(req, resp);
	}

}
