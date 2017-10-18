package com.bo.shiro.test;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Description 密码加密登录验证测试
 * @author 王博
 * @version 2017年10月14日　下午3:02:39
 */
public class PasswrodServiceTest extends ShiroTest {

	@Test
	public void test(){
//		testWithMyRealmD();
//		testWithJdbcRealm();
//		testGeneratePassword();
//		testHashedCredentialsMatcherWithMyRealmE();
//		testHashedCredentialsMatcherWithJdbcRealm();
		testRetryLimitHashedCredentialsMatcherWithMyRealmE();
	}
	
	public void testWithMyRealmD() {
		login("classpath:shiro/authc/shiro_passwordservice.ini", "bo", "123456");
	}
	
	public void testWithJdbcRealm(){
		login("classpath:shiro/authc/shiro_jdbc_passwordservice.ini","shiro02","12345");// 密码:$NieQminDE4Ggcewn98nKl3Jhgq7Smn3dLlQ1MyLPswq7njpt8qwsIP4jQ2MR1nhWTQyNMFkwV19g4tPQSBhNeQ==
	}

	public void testHashedCredentialsMatcherWithMyRealmE(){
		// 使用testGeneratePassword生成的散列密码
		login("classpath:shiro/authc/shiro_hashedCredentialsMatcher.ini", "bo", "12345");
	}
	
	public void testHashedCredentialsMatcherWithJdbcRealm(){
		// Shiro1.2 默认使用了apache commons BeanUtils，默认是不进行Enum类型转型的，此时需要自己注册一个Enum转换器(将配置的COLUMN串转换成Enum类型)
		// BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);// shiro1.4已不支持
		// 使用testGeneratePassword生成的散列密码(pass=d95198b72b895db7a7b6b986560304b0 pass_salt=c52e6d8899dc63fa0eb572e2a9e0e471)
		login("classpath:shiro/authc/shiro_jdbc_hashedCredentialsMatcher.ini","shiro03","12345");
	}
	
	public void testRetryLimitHashedCredentialsMatcherWithMyRealmE() {
		for (int i = 1; i <= 5; i++) {
			try {
				login("classpath:shiro/authc/shiro_retryLimitHashedCredentialsMatcher.ini", "liu", "234");
			} catch (Exception e) {
				/* ignore */
			}
		}
		login("classpath:shiro/authc/shiro_retryLimitHashedCredentialsMatcher.ini", "liu", "12345");
	}
	
    /**
     * @Description 使用MD5算法“密码+盐(用户名+随机数)”的方式生成密码散列值
     */
    public void testGeneratePassword() {
        String algorithmName = "md5";
        String username = "shiro03";
        String password = "12345";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;// 散列次数

        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(salt2);
        System.out.println(encodedPassword);
    }
	
    private class EnumConverter extends AbstractConverter {
    	
        @Override
        protected String convertToString(final Object value) throws Throwable {
            return ((Enum) value).name();
        }
        
        @Override
        protected Object convertToType(final Class type, final Object value) throws Throwable {
            return Enum.valueOf(type, value.toString());
        }

        @Override
        protected Class getDefaultType() {
            return null;
        }

    }
    
}
