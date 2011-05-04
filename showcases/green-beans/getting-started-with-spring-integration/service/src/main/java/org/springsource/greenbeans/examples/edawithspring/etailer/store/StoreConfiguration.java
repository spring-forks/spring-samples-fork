/*
* Copyright 2006-2007 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.springsource.greenbeans.examples.edawithspring.etailer.store;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup;
import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.h2.Driver;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.integration.xml.transformer.ResultToStringTransformer;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springsource.greenbeans.examples.edawithspring.etailer.common.CommonConfiguration;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.util.Properties;

/**
 * Java-configuration for the store itself
 *
 * @author Josh Long
 */
@Configuration
@SuppressWarnings("unused")
public class StoreConfiguration  extends CommonConfiguration{

  @Value("${dataSource.driverClassName}")
  private String driverName;

  @Value("${dataSource.url}")
  private String url;

  @Value("${dataSource.user}")
  private String user;

  @Value("${dataSource.password}")
  private String password;

  @Value("${jms.broker.url}")
  private String brokerUrl;

  @Value("${jms.partnernotifications.destination}")
  private String partnerNotificationsDestination;

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    localContainerEntityManagerFactoryBean.setPersistenceUnitName("persistenceUnit");
    localContainerEntityManagerFactoryBean.setDataSource(this.dataSource());
    localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());

    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", H2Dialect.class.getName());
    properties.setProperty("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
    properties.setProperty("hibernate.transaction.manager_lookup_class", TransactionManagerLookup.class.getName());
    localContainerEntityManagerFactoryBean.setJpaProperties(properties);
    return localContainerEntityManagerFactoryBean;
  }

  @Bean
  public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setDatabase(Database.H2);
    hibernateJpaVendorAdapter.setShowSql(true);
    hibernateJpaVendorAdapter.setDatabasePlatform(H2Dialect.class.getName());
    return hibernateJpaVendorAdapter;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor = new PersistenceExceptionTranslationPostProcessor();
    persistenceExceptionTranslationPostProcessor.setRepositoryAnnotationType(Service.class);
    return persistenceExceptionTranslationPostProcessor;
  }

  @Bean(initMethod = "init", destroyMethod = "close")
  public DataSource dataSource() {

    AtomikosNonXADataSourceBean xaDataSourceBean = new AtomikosNonXADataSourceBean();
    xaDataSourceBean.setDriverClassName(Driver.class.getName());
    xaDataSourceBean.setUrl(this.url);
    xaDataSourceBean.setPassword(this.password);
    xaDataSourceBean.setUser(this.user);
    xaDataSourceBean.setUniqueResourceName("xads1");

    return xaDataSourceBean;
  }

  @Bean(initMethod = "init", destroyMethod = "close")
  public ConnectionFactory connectionFactory() {
    ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory();
    activeMQXAConnectionFactory.setBrokerURL(this.brokerUrl);

    AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
    atomikosConnectionFactoryBean.setUniqueResourceName("xamq1");
    atomikosConnectionFactoryBean.setLocalTransactionMode(false);
    atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
    return atomikosConnectionFactoryBean;
  }


  @Bean
  public MarshallingMessageConverter marshallingMessageConverter() {
    return new MarshallingMessageConverter(marshaller());
  }

  @Bean public ResultToStringTransformer resultToStringTransformer(){
    return new ResultToStringTransformer();
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate(this.connectionFactory());
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }

  @Bean(initMethod = "init", destroyMethod = "close")
  public TransactionManager userTransactionManager() {
    UserTransactionManager userTransactionManager = new UserTransactionManager();
    userTransactionManager.setForceShutdown(false);
    return userTransactionManager;
  }

  @Bean
  public UserTransaction userTransaction() throws Exception {
    UserTransactionImp userTransactionImp = new UserTransactionImp();
    userTransactionImp.setTransactionTimeout(1000);
    return userTransactionImp;
  }

  @Bean
  public PlatformTransactionManager transactionManager() throws Exception {
    return new JtaTransactionManager(this.userTransaction(), this.userTransactionManager());
  }
}
