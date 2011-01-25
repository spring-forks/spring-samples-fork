package org.springsource.greenbeans.examples.amqp.messagelistenercontainer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springsource.greenbeans.examples.Customer;

@Component
public class MessageListenerContainerConsumer {

  private Log log = LogFactory.getLog(getClass() );

  public void handleMessage(Customer cu){
    log.info("Received customer " + ToStringBuilder.reflectionToString(cu)) ;
  }
}
