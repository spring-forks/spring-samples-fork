package org.springsource.greenbeans.examples.amqp.core;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.greenbeans.examples.Customer;

@Component
public class Producer {

  @Value("${amqp.customer.exchange}")
  private String exchange;

  @Value("${amqp.customer.queue}")
  private String routingKey;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  private Log log = LogFactory.getLog(getClass());

  @Transactional
  public void sendCustomerUpdate(Customer customer) {
    log.info("sending customer update " + ToStringBuilder.reflectionToString(customer));
    this.rabbitTemplate.convertAndSend(this.exchange , this.routingKey, customer);
  }
}
