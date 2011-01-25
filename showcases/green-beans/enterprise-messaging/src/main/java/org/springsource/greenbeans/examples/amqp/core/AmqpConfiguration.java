package org.springsource.greenbeans.examples.amqp.core;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SingleConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class AmqpConfiguration {

  @Value("${broker.url}")
  private String brokerUrl;

  @Value("${broker.username}")
  private String username;

  @Value("${broker.password}")
  private String password;

  @Value("${amqp.customer.queue}")
  private String customerQueueName;

  @Bean
  public RabbitTemplate rabbitTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(singleConnectionFactory());
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public RabbitTransactionManager rabbitTransactionManager() {
    return new RabbitTransactionManager(this.singleConnectionFactory());
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new JsonMessageConverter();
  }

  @Bean
  public ConnectionFactory singleConnectionFactory() {
    SingleConnectionFactory connectionFactory = new SingleConnectionFactory(this.brokerUrl);
    connectionFactory.setUsername(this.username);
    connectionFactory.setPassword(this.password);
    return connectionFactory;
  }

  @Bean
  public AmqpAdmin amqpAdmin() {
    return new RabbitAdmin(this.rabbitTemplate());
  }


  @Bean
  public Queue customerQueue() {
    Queue q = new Queue(this.customerQueueName);
    amqpAdmin().declareQueue(q);
    return q;
  }

  @Bean
  public DirectExchange customerExchange() {
    DirectExchange directExchange = new DirectExchange(customerQueueName);
    this.amqpAdmin().declareExchange(directExchange);
    return directExchange ;
  }

  @Bean
  public Binding marketDataBinding() {
    return BindingBuilder.from(customerQueue()).to(customerExchange()).with(this.customerQueueName);
  }

}
