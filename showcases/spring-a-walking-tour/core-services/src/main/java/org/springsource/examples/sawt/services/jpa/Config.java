package org.springsource.examples.sawt.services.jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springsource.examples.sawt.services.model.Customer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
public class Config {

    @Value("${dataSource.driverClass}")
    private Class driverClass;

    @Value("${dataSource.url}")
    private String url;

    @Value("${dataSource.user}")
    private String user;

    @Value("${dataSource.password}")
    private String password;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan( new String []{Customer.class.getPackage().getName()} );

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter() ;
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter( jpaVendorAdapter );

        // look ma, no persistence.xml !
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setPassword(this.password);
        simpleDriverDataSource.setUrl(this.url);
        simpleDriverDataSource.setUsername(this.user);
        simpleDriverDataSource.setDriverClass(driverClass);
        return simpleDriverDataSource;
    }
}
