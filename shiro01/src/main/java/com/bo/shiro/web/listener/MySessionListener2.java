package com.bo.shiro.web.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * @Description shiro session监听器2 (通过继承适配器监听指定事件)
 * @author 王博
 * @version 2017年11月2日　上午1:06:17
 */
public class MySessionListener2 extends SessionListenerAdapter {

	@Override
	public void onStart(Session session) {
		System.out.println("会话创建：" + session.getId());
	}

}
