package org.springsource.examples.crm.services.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springsource.examples.crm.services.config.CrmConfiguration;

import javax.persistence.EntityManagerFactory;

/**
 * sets up JPA machinery
 *
 * @author Josh Long
 */
@Configuration
public class JpaConfiguration extends CrmConfiguration {

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    localContainerEntityManagerFactoryBean.setDataSource(this.dataSource());
    return localContainerEntityManagerFactoryBean;
  }

  // this is required to replace JpaTemplate's exception translation
  @Bean
  public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor =  new PersistenceExceptionTranslationPostProcessor();
    persistenceExceptionTranslationPostProcessor.setRepositoryAnnotationType( Service.class);
    return persistenceExceptionTranslationPostProcessor;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
    return new JpaTransactionManager(entityManagerFactory);
  }
}
