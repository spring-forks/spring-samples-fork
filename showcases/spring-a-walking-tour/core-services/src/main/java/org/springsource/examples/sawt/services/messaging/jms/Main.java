package org.springsource.examples.sawt.services.messaging.jms;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.Assert;
import org.springsource.examples.sawt.services.model.Customer;

import javax.jms.Message;
import javax.jms.TextMessage;

public class Main {
    public static void main(String[] args) throws Exception {

        Log log = LogFactory.getLog(Main.class);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/org/springsource/examples/sawt/services/messaging/jms/context.xml");

        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);

        Customer customer = new Customer("Mario", "Gray");

        String queue = "customers";

        jmsTemplate.convertAndSend(queue, customer);
        jmsTemplate.convertAndSend(queue, customer); // weve sent the same message, twice

        Customer ogCustomer = (Customer) jmsTemplate.receiveAndConvert(queue);
        log.info("converted message: " + ToStringBuilder.reflectionToString(ogCustomer));

        Message m = jmsTemplate.receive(queue);
        Assert.isInstanceOf(TextMessage.class, m);
        TextMessage message = (TextMessage) m;
        log.info("unconverted message: " + message.getText());


    }
}
