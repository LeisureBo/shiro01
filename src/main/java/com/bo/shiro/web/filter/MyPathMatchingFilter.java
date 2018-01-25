package com.bo.shiro.web.filter;

import java.util.Arrays;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.PathMatchingFilter;

/**
 * @Description 扩展PathMatchingFilter
 *              PathMatchingFilter继承了AdviceFilter，提供了url模式过滤的功能
 *              ，如果需要对指定的请求进行处理，可以扩展PathMatchingFilter
 * @author 王博
 * @version 2017年10月30日　下午5:27:27
 */
public class MyPathMatchingFilter extends PathMatchingFilter {

	/** shiro配置:
	 * [filters]
	 * myFilter3=com.bo.shiro.web.filter.MyPathMatchingFilter
	 * [urls]
	 * /**= myFilter3[config];
	 * 备注:"/**"就是注册给PathMatchingFilter的url模式,config就是拦截器的配置参数,多个之间逗号分隔,onPreHandle使用mappedValue接收参数值
	 **/

	/**
	 * @Description 该方法用于path与请求路径进行匹配的方法,如果匹配返回true.
	 */
	@Override
	protected boolean pathsMatch(String path, ServletRequest request) {
		return super.pathsMatch(path, request);
	}
	
	
	/**
	 * @Description 会进行url模式与请求url进行匹配,如果匹配会调用onPreHandle;如果没有配置url模式/没有url模式匹配,默认直接返回true
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		return super.preHandle(request, response);
	}

	/**
	 * @Description 
	 * 在preHandle中,当pathsMatch匹配一个路径后,会调用opPreHandler方法并将路径绑定参数配置传给mappedValue;
	 * 然后可以在这个方法中进行一些验证(如角色授权),如果验证失败可以返回false中断流程(默认什么也不处理返回true);
	 * 子类只实现onPreHandle即可,无须实现preHandle.如果没有path与请求路径匹配,preHandle默认返回true.
	 */
	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		System.out.println("MyPathMatchingFilter: url matches, config is " + Arrays.toString((String[]) mappedValue));
		return true;
	}

}
