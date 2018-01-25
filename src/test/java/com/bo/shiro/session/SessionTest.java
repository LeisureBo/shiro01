package com.bo.shiro.session;

import static org.junit.Assert.*;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import com.bo.shiro.test.ShiroTest;

/**
 * @Description 
 * @author 王博
 * @version 2017年12月28日　下午7:53:24
 */
public class SessionTest extends ShiroTest {

	@Test
	public void sessionTest() throws Exception {
		login("classpath:shiro/session/shiro_session_test.ini", "bo", "123");
		
		Session session = subject().getSession();
		System.out.println("Id:"+session.getId());//获取会话ID
		System.out.println("Host:"+session.getHost());//获取当前登录用户主机地址(如果未知，返回null)
		System.out.println("Timeout:"+session.getTimeout());//获取超时时间(默认30分钟)
		System.out.println("StartTimestamp:"+session.getStartTimestamp());//获取会话创建时间
		System.out.println("LastAccessTime:"+session.getLastAccessTime());//获取最后访问时间
		
		Thread.sleep(1000L);
        session.touch();//更新会话最后访问时间
        System.out.println(session.getLastAccessTime());
        
        //会话属性操作
        session.setAttribute("key", "123");
        Assert.assertEquals("123", session.getAttribute("key"));
        session.removeAttribute("key");
	}

}
