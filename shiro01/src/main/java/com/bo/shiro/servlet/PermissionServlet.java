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
 * @version 2017年9月21日　下午10:03:55
 */
@WebServlet("/permission")
public class PermissionServlet extends HttpServlet {

	private static final long serialVersionUID = 1293767291362099863L;

	public PermissionServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("shiro/admin/perms.jsp").forward(req, resp);
	}

}
