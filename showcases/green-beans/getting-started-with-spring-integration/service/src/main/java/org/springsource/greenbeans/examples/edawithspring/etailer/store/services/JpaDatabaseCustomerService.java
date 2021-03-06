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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class JpaDatabaseCustomerService implements CustomerService {

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional(readOnly = true)
  public Customer getCustomerById(long id) {
    return this.entityManager.find(Customer.class, id);
  }

  @Transactional
  public Customer createCustomer(String fn, String ln) {
    Customer newCustomer = new Customer();
    newCustomer.setFirstName(fn);
    newCustomer.setLastName(ln);
    this.entityManager.persist( newCustomer);
    return newCustomer;
  }
}
