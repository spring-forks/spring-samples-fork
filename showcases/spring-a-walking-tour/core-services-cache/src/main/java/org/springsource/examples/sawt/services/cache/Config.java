package org.springsource.examples.sawt.services.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class Config {

	public static final String CUSTOMERS_REGION = "customers";

	@Value("${dataSource.url}") private String url;

	@Value("${dataSource.user}") private String user;

	@Value("${dataSource.password}") private String password;

	@Value("${dataSource.driverClassName}") private Class driverClassName;

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(this.dataSource());
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
		simpleDriverDataSource.setPassword(this.password);
		simpleDriverDataSource.setUrl(this.url);
		simpleDriverDataSource.setUsername(this.user);
		simpleDriverDataSource.setDriverClass(driverClassName);
		return simpleDriverDataSource;
	}

	@Bean
	public ConcurrentCacheFactoryBean<Object, Object> customersCacheFactoryBean() {
		ConcurrentCacheFactoryBean<Object, Object> concurrentCacheFactoryBean = new ConcurrentCacheFactoryBean<Object, Object>();
		concurrentCacheFactoryBean.setName(CUSTOMERS_REGION);
		concurrentCacheFactoryBean
				.setStore(new ConcurrentHashMap<Object, Object>());
		return concurrentCacheFactoryBean;
	}

	@Bean
	public CacheManager cacheManager() throws Exception {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		Set<Cache<?, ?>> caches = new HashSet<Cache<?, ?>>();
		caches.add(customersCacheFactoryBean().getObject());
		simpleCacheManager.setCaches(caches);
		return simpleCacheManager;
	}

}
