package org.springsource.examples.sawt.services.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Value("${dataSource.url}")
    private String url;

    @Value("${dataSource.user}")
    private String user;

    @Value("${dataSource.password}")
    private String password;

    @Value("${dataSource.driverClass}")
    private Class driverClassName;

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
}
