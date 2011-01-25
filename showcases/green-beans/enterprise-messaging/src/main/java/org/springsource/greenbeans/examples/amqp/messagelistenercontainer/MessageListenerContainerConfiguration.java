package org.springsource.greenbeans.examples.amqp.messagelistenercontainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.connection.SingleConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class MessageListenerContainerConfiguration {

  @Autowired
  private SingleConnectionFactory connectionFactory;

  private Log log = LogFactory.getLog(getClass());

  @Value("${amqp.customer.queue}")
  private String queue;

  @Autowired
  private RabbitTransactionManager rabbitTransactionManager;

  @Autowired
  private JsonMessageConverter messageConverter;

  @Autowired
  private MessageListenerContainerConsumer messageListenerContainerConsumer;

  @Bean
  public SimpleMessageListenerContainer listenerContainer() {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setTransactionManager(this.rabbitTransactionManager);
    container.setConnectionFactory(connectionFactory);
    container.setQueueName(this.queue);

    MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(this.messageListenerContainerConsumer, this.messageConverter);
    container.setMessageListener(messageListenerAdapter);
    return container;
  }
}
