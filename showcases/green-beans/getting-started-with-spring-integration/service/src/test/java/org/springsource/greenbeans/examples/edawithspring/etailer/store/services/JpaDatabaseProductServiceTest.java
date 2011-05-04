/*
* Copyright 2006-2007 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.springsource.greenbeans.examples.edawithspring.etailer.store.services;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Customer;

import static org.junit.Assert.*;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/services.xml"})
public class JpaDatabaseProductServiceTest {

  private String firstName = "John";

  private String lastName = "Doe";

  @Autowired
  private CustomerService customerService;


  @Ignore
  public void testCustomerService() throws Throwable {
    Customer customer = this.customerService.createCustomer(this.firstName, this.lastName);
    assertNotNull(customer);
    assertTrue(customer.getId() > 0);

    Customer customer2 = this.customerService.getCustomerById(customer.getId());
    assertEquals(customer2.getFirstName(), this.firstName);
    assertEquals(customer2.getLastName(), this.lastName);
    assertEquals(customer.getId(), customer2.getId());
  }
}
