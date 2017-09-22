package com.bo.shiro.test;

import static org.junit.Assert.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @Description 
 * @author 王博
 * @version 2017年9月20日　下午9:04:18
 */
public class ShiroTest {

	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @Description 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String permission = "system-edit-10";
		String[] parts = permission.split("\\-");
		for(String part : parts){
			System.out.println(part);
		}
		String password = "12345";
		System.out.println(DigestUtils.md5Hex(password));
		
		System.out.println(UnknownAccountException.class.getName());
	}
	
	@Test
	public void isAjax(){
		String header = "xmlHttpRequest";
		boolean result = "XMLHttpRequest".equalsIgnoreCase(header);
		System.out.println(result);
		assertEquals("error", result, true);
	}
}
