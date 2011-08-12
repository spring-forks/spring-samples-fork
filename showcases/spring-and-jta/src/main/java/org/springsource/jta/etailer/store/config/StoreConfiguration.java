/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.springsource.jta.etailer.store.config;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.inject.Inject;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.util.Properties;

/**
 * Configuration class that's common to all the implementations. This won't change.
 */
@EnableTransactionManagement
@Configuration
@PropertySource("services.properties")
@ComponentScan(value = "org.springsource.jta.etailer.store.services")
public class StoreConfiguration {

// todo to use this, you need to uncomment '@Configuration' on the BitronixJtaConfiguration class.
//    @Inject private BitronixJtaConfiguration jtaConfiguration ;
    @Inject private AtomikosJtaConfiguration jtaConfiguration ;

	@Bean
	public JmsTemplate jmsTemplate() throws Throwable{
		JmsTemplate jmsTemplate = new JmsTemplate(jtaConfiguration.connectionFactory());
		jmsTemplate.setSessionTransacted(true);
		return jmsTemplate;
	}

	@Bean
	public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		hibernateJpaVendorAdapter.setShowSql(true);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManager () throws Throwable  {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setPersistenceUnitName("persistenceUnit");
		entityManager.setDataSource(jtaConfiguration.dataSource());
		entityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter());

		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", MySQL5Dialect.class.getName());
		properties.setProperty("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
		jtaConfiguration.tailorProperties(properties);
		entityManager.setJpaProperties(properties);

		return entityManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
		PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor = new PersistenceExceptionTranslationPostProcessor();
		persistenceExceptionTranslationPostProcessor.setRepositoryAnnotationType(Service.class);
		return persistenceExceptionTranslationPostProcessor;
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager()  throws Throwable {
		UserTransaction userTransaction = jtaConfiguration.userTransaction() ;
		TransactionManager transactionManager = jtaConfiguration.transactionManager() ;
		return new JtaTransactionManager( userTransaction, transactionManager);
	}
}

