package com.bo.shiro.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月22日　下午2:08:54
 */
public class MyWebUtils {
	
	public static  boolean isAjax(HttpServletRequest request){
	    return  (request.getHeader("X-Requested-With") != null  
	    && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With").toString())) ;
	}
}
