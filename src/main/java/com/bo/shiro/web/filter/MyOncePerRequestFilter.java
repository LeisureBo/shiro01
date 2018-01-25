package com.bo.shiro.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

/**
 * @Description 扩展OncePerRequestFilter
 * @author 王博
 * @version 2017年10月30日　下午5:15:34
 */
public class MyOncePerRequestFilter extends OncePerRequestFilter {

	/**
	 * shiro.ini配置文件中配置：
	 * [main]
	 * myFilter1=com.bo.shiro.web.filter.MyOncePerRequestFilter
     * # [filters]
	 * # myFilter1=com.bo.shiro.web.filter.MyOncePerRequestFilter
	 * [urls]
     * /**=myFilter1
     * Filter可以在[main]或[filters]部分注册，然后在[urls]部分配置url与filter的映射关系即可
	 **/

	@Override
	protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		System.out.println("my once per request filter execute..");
		chain.doFilter(request, response);
	}

}
