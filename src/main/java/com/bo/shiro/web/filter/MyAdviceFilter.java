package com.bo.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;

/**
 * @Description 扩展AdviceFilter
 * @author 王博
 * @version 2017年10月30日　下午5:21:20
 */
public class MyAdviceFilter extends AdviceFilter {

	/**
	 * shiro配置: 
	 * [filters]
	 * myFilter1=com.bo.shiro.web.filter.MyOncePerRequestFilter
	 * myFilter2=com.bo.shiro.web.filter.MyAdviceFilter
	 * [urls]
	 * /**=myFilter1,myFilter2;
	 **/

	/**
	 * @Description 进行请求的预处理，然后根据返回值决定是否继续处理(true:继续过滤器链) 可以通过它实现权限控制
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("====预处理/前置处理");
		return true;// 返回false将中断后续拦截器链的执行
	}

	/**
	 * @Description 执行完拦截器链之后正常返回后执行
	 */
	@Override
	protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("====后处理/后置返回处理");
	}

	/**
	 * @Description 不管最后有没有异常，afterCompletion都会执行，完成如清理资源功能
	 */
	@Override
	public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
		System.out.println("====完成处理/后置最终处理");
	}
}
