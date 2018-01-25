package com.bo.shiro.authc.credential;

import java.util.concurrent.atomic.AtomicInteger;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 密码重试次数限制
 * @author 王博
 * @version 2017年10月17日　下午2:58:00
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);
	private static Ehcache passwordRetryCache;

	static {
		CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache/shiro-ehcache.xml"));
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		// retry count + 1
		Element element = passwordRetryCache.get(username);
		if (element == null) {
			element = new Element(username, new AtomicInteger(0));
			passwordRetryCache.put(element);
		}
		// 缓存设置的存活时间为10分钟,即当重试次数用完且缓存失效后才可继续重试
		AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
		logger.info("retry------------------->" + retryCount.get());
		if (retryCount.incrementAndGet() > 5) {
			// if retry count > 5 throw
			throw new ExcessiveAttemptsException();
		}
		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			// clear retry count
			passwordRetryCache.remove(username);
		}
		return matches;
	}

}
