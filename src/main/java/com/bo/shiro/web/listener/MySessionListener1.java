package com.bo.shiro.web.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @Description shiro session监听器1
 * @author 王博
 * @version 2017年11月2日　上午1:05:01
 */
public class MySessionListener1 implements SessionListener {

	@Override
	public void onStart(Session session) {// 会话创建时触发
		System.out.println("MySessionListener1 -> 会话创建：" + session.getId());
	}

	@Override
	public void onExpiration(Session session) {// 会话过期时触发
		System.out.println("MySessionListener1 -> 会话过期：" + session.getId());
	}

	@Override
	public void onStop(Session session) {// 退出/会话过期时触发
		System.out.println("MySessionListener1 -> 会话停止：" + session.getId());
	}

}
