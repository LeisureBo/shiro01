package com.bo.shiro.realm;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import com.bo.shiro.entity.User;
import com.bo.shiro.test.ShiroTest;

/**
 * @Description 
 * @author 王博
 * @version 2017年10月30日　下午1:30:32
 */
public class PrincipalCollectionTest extends ShiroTest {

	@Test
	public void test() {
		// 因为Realm里没有进行验证，所以相当于每个Realm都身份验证成功了
        login("classpath:shiro/authc/test_shiro_multi_realm.ini", "bo", "123");
        Subject subject = subject();
        // 获取Primary Principal(即返回第一个)
        Object primaryPrincipal1 = subject.getPrincipal();
        PrincipalCollection princialCollection = subject.getPrincipals();// 获取所有验证通过的身份
//		System.out.println(princialCollection);
        Object primaryPrincipal2 = princialCollection.getPrimaryPrincipal();

        // 但是因为多个Realm都返回了Principal，所以此处到底是哪个是不确定的
        Assert.assertEquals(primaryPrincipal1, primaryPrincipal2);

        // 获取所有身份验证成功的Realm名字
        Set<String> realmNames = princialCollection.getRealmNames();
        System.out.println(realmNames);

        // 因为MyRealm1和MyRealm2返回的凭据都是bo，所以排重了
        // 将身份信息转换为Set/List，即使转换为List，也是先转换为Set再完成的
		Set<Object> principals = princialCollection.asSet(); // asList和asSet的结果一样
		System.out.println(principals);

        // 根据Realm名字获取身份，因为Realm名字可以重复，所以可能多个身份，建议Realm名字尽量不要重复
		Collection<User> users = princialCollection.fromRealm("com.bo.shiro.realm.MyRealm3");
		System.out.println(users);
	}

}
