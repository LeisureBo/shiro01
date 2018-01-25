package com.bo.shiro.common;

import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Description
 * @author 王博
 * @version 2017年10月18日　下午2:07:25
 */
public class JdbcTemplateUtils {

	private static JdbcTemplate jdbcTemplate;

	public static JdbcTemplate jdbcTemplate() {
		if (jdbcTemplate == null) {
			synchronized(JdbcTemplateUtils.class){
				if(jdbcTemplate == null){
					jdbcTemplate = createJdbcTemplate();
				}
			}
		}
		return jdbcTemplate;
	}

	private static JdbcTemplate createJdbcTemplate() {
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/shiro");
		ds.setUsername("root");
		ds.setPassword("root");
		return new JdbcTemplate(ds);
	}
}
