package org.springsource.jta.etailer.store.config;

import bitronix.tm.TransactionManagerServices;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import bitronix.tm.resource.jms.PoolingConnectionFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.hibernate.transaction.BTMTransactionManagerLookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.util.Properties;

// todo to use this one instead, uncomment this and then comment out the annotation on AtomikosJtaConfiguration
//@Configuration
public class BitronixJtaConfiguration   {


    @Inject
    private Environment environment ;

	private int maxPoolSize = 5;

	@Bean(destroyMethod = "close")
	public ConnectionFactory connectionFactory() {
		PoolingConnectionFactory poolingConnectionFactory = new PoolingConnectionFactory();
		poolingConnectionFactory.setClassName(ActiveMQXAConnectionFactory.class.getName());
		poolingConnectionFactory.setMaxPoolSize(this.maxPoolSize);
		poolingConnectionFactory.setUniqueName("xamq");

		Properties properties = new Properties();
		properties.setProperty("brokerURL", this.environment.getProperty("jms.broker.url"));
		poolingConnectionFactory.setDriverProperties(properties);
		poolingConnectionFactory.init();
		return poolingConnectionFactory;
	}

	@Bean
	public bitronix.tm.Configuration transactionManagerServices() {
		return TransactionManagerServices.getConfiguration();
	}

	public void tailorProperties(Properties properties) {
		properties.setProperty("hibernate.transaction.manager_lookup_class", BTMTransactionManagerLookup.class.getName());
	}

	@Bean
	public TransactionManager transactionManager() {
		return TransactionManagerServices.getTransactionManager();
	}

	@Bean
	public UserTransaction userTransaction() {
		return TransactionManagerServices.getTransactionManager();
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {

		PoolingDataSource poolingDataSource = new PoolingDataSource();
		poolingDataSource.setClassName(MysqlXADataSource.class.getName());
		poolingDataSource.setUniqueName("xads");

		Properties props = new Properties();
		props.put("pinGlobalTxToPhysicalConnection","true");
		props.put("password", this.environment.getProperty("dataSource.password"));
		props.setProperty("user", this.environment.getProperty("dataSource.user"));
		props.setProperty("url", this.environment.getProperty("dataSource.url"));

		poolingDataSource.setDriverProperties(props);
		poolingDataSource.setMaxPoolSize(this.maxPoolSize);
		poolingDataSource.init();

		return poolingDataSource;
	}
}
