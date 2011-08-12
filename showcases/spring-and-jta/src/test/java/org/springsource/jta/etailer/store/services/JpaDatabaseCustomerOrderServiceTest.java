/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.springsource.jta.etailer.store.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springsource.jta.etailer.store.config.AtomikosJtaConfiguration;
import org.springsource.jta.etailer.store.config.BitronixJtaConfiguration;
import org.springsource.jta.etailer.store.config.StoreConfiguration;
import org.springsource.jta.etailer.store.domain.Customer;
import org.springsource.jta.etailer.store.domain.LineItem;
import org.springsource.jta.etailer.store.domain.Product;
import org.springsource.jta.etailer.store.domain.Purchase;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {/* BitronixJtaConfiguration.class  AtomikosJtaConfiguration.class */AtomikosJtaConfiguration.class , StoreConfiguration.class})
public class JpaDatabaseCustomerOrderServiceTest {

	private Log log = LogFactory.getLog(getClass().getName());

	@Inject private CustomerOrderService customerOrderService;

	@Inject private CustomerService customerService;

	@Inject private ProductService productService;

	@Before
	public void before() throws Throwable {
		log.info("ensure that MySQL and ActiveMQ are running! " +
				 "For specifics, see the README.txt in this project");
	}

	@Test
	public void testAddingProductsToCart() throws Exception {


		Customer customer = customerService.createCustomer("A", "Customer");

		Purchase purchase = customerOrderService.createPurchase(customer.getId());

		Product product1 = productService.createProduct("Widget1", "a widget that slices (but not dices)", 12.0);
		Product product2 = productService.createProduct("Widget2", "a widget that dices (but not slices)", 7.5);

		LineItem one = customerOrderService.addProductToPurchase(purchase.getId(), product1.getId());
		LineItem two = customerOrderService.addProductToPurchase(purchase.getId(), product2.getId());

		purchase = customerOrderService.getPurchaseById(purchase.getId());
		assertTrue(purchase.getTotal() == (product1.getPrice() + product2.getPrice()));

		assertEquals(one.getPurchase().getId(), purchase.getId());
		assertEquals(two.getPurchase().getId(), purchase.getId());

		// this part requires XA to work correctly
		customerOrderService.checkout(purchase.getId());
	}
}
