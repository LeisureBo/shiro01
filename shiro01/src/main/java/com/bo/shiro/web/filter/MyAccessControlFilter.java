package com.bo.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * @Description 扩展AccessControlFilter
 * @author 王博
 * @version 2017年10月30日　下午5:41:40
 */
public class MyAccessControlFilter extends AccessControlFilter {
	
	/** shiro配置: 
	 * [filters]
	 * myFilter4=com.bo.shiro.web.filter.MyAccessControlFilter
	 * [urls]
	 * /**=myFilter4
	 **/

	/**
	 * @Description 即是否允许访问，返回true表示允许
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		System.out.println("access allowed");
		return true;
	}

	/**
	 * @Description 表示访问拒绝时是否自己处理,如果返回true表示自己不处理且继续拦截器链执行,返回false表示自己已经处理了(比如重定向到另一个页面)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("访问被拒绝也不进行处理，继续拦截器链的执行");
		return true;
	}

}
