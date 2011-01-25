package org.springsource.greenbeans.examples.amqp.amqptemplate;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.greenbeans.examples.Customer;

@Component
public class RawAmqpTemplatePollingMessageConsumer {

  @Autowired
  protected volatile RabbitTemplate amqpTemplate;

  @Value("${amqp.customer.queue}")
  private String queue;

  private Log log = LogFactory.getLog(getClass());

  @Transactional
  public void receiveAndProcessCustomerUpdates() throws Exception {
    Customer msg = (Customer)this.amqpTemplate.receiveAndConvert(this.queue);
    log.info("receiving customer message: " + ToStringBuilder.reflectionToString(  msg));
  }
}
