package org.springframework.samples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CustomerClient {

  private CustomerService customerService;

  @Autowired
  public void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }

  public void printCustomerInformation(long customerId) {
    Customer customer = this.customerService.getCustomerById(customerId);
    System.out.println(customer);
  }

  public static void main(String[] args) throws Exception {
    GenericXmlApplicationContext genericXmlApplicationContext = new GenericXmlApplicationContext("context1.xml");
    CustomerClient customerClient = genericXmlApplicationContext.getBean(CustomerClient.class);
    customerClient.printCustomerInformation(1);
  }
}
