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
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.LineItem;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Product;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Purchase;

import static org.junit.Assert.assertTrue;

/**
 * @author Josh Long
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/services.xml"})
public class JpaDatabaseCustomerOrderServiceTest {

  @Autowired
  private ProductService productService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CustomerOrderService customerOrderService;

  private void addProducts() throws Exception {
    Customer customer = this.customerService.createCustomer("A", "Customer");

    Purchase purchase = this.customerOrderService.createPurchase(customer.getId());

    double price1 = 12, price2 = 7.5;

    Product product1 = this.productService.createProduct("Widget1", "a widget that slices (but not dices)", price1),
        product2 = this.productService.createProduct("Widget2", "a widget that dices (but not slices)", price2);

    LineItem one = this.customerOrderService.addProductToPurchase(purchase.getId(), product1.getId());
    LineItem two = this.customerOrderService.addProductToPurchase(purchase.getId(), product2.getId());

    purchase = this.customerOrderService.getPurchaseById(purchase.getId());
    assertTrue(purchase.getTotal() == (price1 + price2));

    this.customerOrderService.checkout(purchase.getId());

    /* Date date = new Date();
   this.customerOrderService.setLineItemShipped(one.getId(), date);
   this.customerOrderService.setLineItemShipped(two.getId(), date);*/
  }

  @Ignore
  public void testAddingProducts() throws Exception {
    for (int i = 0; i < 5; i++) addProducts();
  }
}
