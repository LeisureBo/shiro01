package com.bo.shiro.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月21日　下午9:56:37
 */
@WebServlet("/role")
public class RoleServlet extends HttpServlet {

	private static final long serialVersionUID = 3261711695138320734L;

	public RoleServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/shiro/admin/role.jsp").forward(req, resp);
	}

}
