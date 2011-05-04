
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
package org.springsource.greenbeans.examples.edawithspring.etailer.backoffice;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jms.connection.JmsTransactionManager;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;

/**
 * holds interesting objects for back office processing
 *
 * @author Josh Long
 */
@Configuration
public class PartnerNotificationConfiguration {

  @Value("${jms.broker.url}")
  private String brokerUrl;

  @Value("${jms.partnernotifications.destination}")
  private String partnerNotificationsDestination;

/*  @Value("${ftps.user}")
  private String user;

  @Value("${ftps.password}")
  private String password;

  @Value("${ftps.host}")
  private String host;*/

  @Value("${dataSource.driverClassName}")
  private String driverName;

  @Value("${dataSource.url}")
  private String dsUrl;

  @Value("${dataSource.user}")
  private String dsUser;

  @Value("${dataSource.password}")
  private String dsPassword;

  @Bean
  public DataSourceTransactionManager dataSourceTransactionManager() {
    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    dataSourceTransactionManager.setDataSource(this.dataSource());
    return dataSourceTransactionManager;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(this.dataSource());
  }

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
    simpleDriverDataSource.setPassword(this.dsPassword);
    simpleDriverDataSource.setUsername(this.dsUser);
    simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
    simpleDriverDataSource.setUrl(this.dsUrl);
    return simpleDriverDataSource;
  }

  @Bean
  public JmsTransactionManager jmsTransactionManager() {
    return new JmsTransactionManager(this.connectionFactory());
  }

/*

  todo replace this with a webservice call!


  @Bean
  public DefaultFtpsSessionFactory ftpSession() throws Exception {
    DefaultFtpsSessionFactory defaultFtpSessionFactory = new DefaultFtpsSessionFactory();
    defaultFtpSessionFactory.setUsername(user);
    defaultFtpSessionFactory.setPassword(this.password);
    defaultFtpSessionFactory.setHost(this.host);
    defaultFtpSessionFactory.setImplicit(false);
    defaultFtpSessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);

    return defaultFtpSessionFactory;
  }

*/

  @Bean
  public ConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory activeMQXAConnectionFactory = new ActiveMQConnectionFactory();
    activeMQXAConnectionFactory.setBrokerURL(this.brokerUrl);
    return activeMQXAConnectionFactory;
  }
}
