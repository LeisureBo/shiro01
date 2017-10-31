package com.bo.shiro.web.env;

import javax.servlet.Filter;

import org.apache.shiro.util.ClassUtils;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

/**
 * @Description 自定义FilterChainResolver
 * @author 王博
 * @version 2017年10月30日　下午5:07:11
 */
public class MyIniWebEnvironment extends IniWebEnvironment {

	/**
	 * 此处自己去实现注册filter，及url模式与filter之间的映射关系,
	 * 可通过定制FilterChainResolver或FilterChainManager来完成诸如动态URL匹配的实现。
	 * 然后在web.xml中进行如下配置Environment: 
	 * <context-param>
	 * <param-name>shiroEnvironmentClass</param-name>
	 * <param-value>com.github.zhangkaitao.shiro.chapter8.web.env.MyIniWebEnvironment</param-value>
	 * </context-param>
	 **/
	@Override
	protected FilterChainResolver createFilterChainResolver() {
		/** 在此处扩展自己的FilterChainResolver */
		// 1、创建FilterChainResolver
		PathMatchingFilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();
		// 2、创建FilterChainManager
		DefaultFilterChainManager filterChainManager = new DefaultFilterChainManager();
		// 3、注册Filter
		for (DefaultFilter filter : DefaultFilter.values()) {
			filterChainManager.addFilter(filter.name(), (Filter) ClassUtils.newInstance(filter.getFilterClass()));
		}
		// 4、注册URL-Filter的映射关系
		filterChainManager.addToChain("/login.jsp", "authc");
		filterChainManager.addToChain("/unauthorized.jsp", "anon");
		filterChainManager.addToChain("/**", "authc");
		filterChainManager.addToChain("/**", "roles", "admin");

		// 5、设置Filter的属性
		FormAuthenticationFilter authcFilter = (FormAuthenticationFilter) filterChainManager.getFilter("authc");
		authcFilter.setLoginUrl("/login.jsp");
		RolesAuthorizationFilter rolesFilter = (RolesAuthorizationFilter) filterChainManager.getFilter("roles");
		rolesFilter.setUnauthorizedUrl("/unauthorized.jsp");

		filterChainResolver.setFilterChainManager(filterChainManager);
		return filterChainResolver;
		// return super.createFilterChainResolver();
	}

}
