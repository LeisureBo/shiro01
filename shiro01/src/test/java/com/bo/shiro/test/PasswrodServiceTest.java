package com.bo.shiro.test;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Description 密码加密登录验证测试
 * @author 王博
 * @version 2017年10月14日　下午3:02:39
 */
public class PasswrodServiceTest extends ShiroTest{


	@Test
	public void test() {
		login("classpath:shiro/encrypt/shiro_passwordservice.ini", "bo", "123456");
	}

}
