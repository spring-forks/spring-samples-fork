package org.springsource.greenbeans.examples.jms.messagelistenercontainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springsource.greenbeans.examples.Customer;

import java.util.Map;

@Component
public class MessageListenerContainerConsumer {

  private Log log = LogFactory.getLog(getClass());

  public void receiveMessage(Map<String, Object> message) throws Exception {
    String firstName = (String) message.get("firstName");
    String lastName = (String) message.get("lastName");
    String email = (String) message.get("email");
    Long id = (Long) message.get("id");
    Customer customer = new Customer(id, firstName, lastName, email);
    log.info("receiving customer message: " + customer);
  }
}
