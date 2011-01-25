package org.springsource.greenbeans.examples.amqp.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springsource.greenbeans.examples.Customer;
import org.springsource.greenbeans.examples.amqp.amqptemplate.RawAmqpTemplatePollingMessageConsumer;

public class Main {

  static void runAmqpTemplateConsumer() throws Throwable {
    ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:/amqp-amqptemplate-consumer.xml");
    classPathXmlApplicationContext.start();

    Producer producer = classPathXmlApplicationContext.getBean(Producer.class);
    producer.sendCustomerUpdate(new Customer(1, "John", "Doe", "jd@email.com"));

    RawAmqpTemplatePollingMessageConsumer rawAmqpTemplatePollingMessageConsumer = classPathXmlApplicationContext.getBean(RawAmqpTemplatePollingMessageConsumer.class);
    rawAmqpTemplatePollingMessageConsumer.receiveAndProcessCustomerUpdates();
  }

  static void runMessageListenerContainer() throws Throwable {
    ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:/amqp-message-listener-container-consumer.xml");
    classPathXmlApplicationContext.start();
    Producer producer = classPathXmlApplicationContext.getBean(Producer.class);
    producer.sendCustomerUpdate(new Customer(1, "John", "Doe", "jd@email.com"));
  }

  public static void main(String args[]) throws Throwable {
  runMessageListenerContainer();
     //runAmqpTemplateConsumer();
  }
}
