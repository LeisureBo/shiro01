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

import com.bo.shiro.common.ShiroUtils;

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
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
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
		
		if(subject.isAuthenticated()){// 登录成功返回欢迎界面
			req.setAttribute("subject", subject);
			req.getRequestDispatcher("/shiro/user/welcome.jsp").forward(req, resp);
		} else {// 登录失败返回首页
			req.setAttribute("msg", msg);
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
		
	}
	
}
