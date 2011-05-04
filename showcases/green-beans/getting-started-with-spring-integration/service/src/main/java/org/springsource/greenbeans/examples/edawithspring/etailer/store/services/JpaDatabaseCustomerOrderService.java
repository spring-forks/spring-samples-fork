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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Customer;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.LineItem;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Product;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Purchase;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Set;

/**
 * @author Josh Long
 */
@Service
public class JpaDatabaseCustomerOrderService implements CustomerOrderService {

  private Log log = LogFactory.getLog(getClass());

  @PersistenceContext
  private EntityManager entityManager;

  private CustomerService customerService;
  private ProductService productService;

  private MessagingTemplate messagingTemplate = new MessagingTemplate();

  @Autowired
  private PartnerNotificationService partnerNotificationService ;
  /*
  @Autowired @Qualifier("partnerNotifications") private MessageChannel messageChannel ;
*/
  @Override
  @Transactional
  public void checkout(long purchaseId) {
    Purchase purchase = getPurchaseById(purchaseId);

    if (purchase.isFrozen())
      throw new RuntimeException("you can't check out Purchase(#" + purchase.getId() + ") that's already been checked out!");

    Date purchasedDate = new Date();
    Set<LineItem> lis = purchase.getLineItems();
    for (LineItem lineItem : lis) {
      lineItem.setPurchasedDate(purchasedDate);
      entityManager.merge(lineItem);
    }
    purchase.setFrozen(true);
    entityManager.merge(purchase);
    log.debug("saved purchase updates");


    this.partnerNotificationService.publishNotification( purchase );

   /* Message<Purchase> msg = MessageBuilder.withPayload(purchase).build();
    this.messagingTemplate.send(messageChannel,msg);*/

    log.debug("sent partner notification");
  }

  @Transactional
  public void setLineItemPurchased(long lineItem, Date pd) {
    LineItem li = getLineItemById(lineItem);
    li.setPurchasedDate(pd);
    entityManager.merge(li);
  }

  @Transactional
  public void setLineItemShipped(long lineItem, Date shippedDate) {
    LineItem li = getLineItemById(lineItem);

    if (!li.hasBeenPurchased())
      throw new RuntimeException("you can't ship a PurchaseLineItem (#" + li.getId() + ") that hasn't been purchased!");

    li.setShippedDate(shippedDate);
    entityManager.merge(li);
  }

  @Transactional
  public void setPurchaseShipped(long purchaseId, Date shippedDate){
    Purchase purchase = getPurchaseById( purchaseId );
    for (LineItem lineItem : purchase.getLineItems() )
      setLineItemShipped( lineItem.getId() , shippedDate);
  }

  @Transactional(readOnly = true)
  public LineItem getLineItemById(long lineItemId) {
    return entityManager.find(LineItem.class, lineItemId);
  }

  @Transactional
  public Purchase createPurchase(long customerId) {
    Purchase purchase = new Purchase();
    Customer customer = this.customerService.getCustomerById(customerId);
    purchase.setCustomer(customer);
    purchase.setTotal(0);
    entityManager.persist(purchase);
    return purchase;
  }

  private void recalculate(Purchase p) {
    double total = 0;
    Set<LineItem> lis = p.getLineItems();
    for (LineItem lineItem : lis)
      total += lineItem.getProduct().getPrice();
    p.setTotal(total);
  }

  @Transactional(readOnly = true)
  public Purchase getPurchaseById(long purchaseId) {
    return entityManager.find(Purchase.class, purchaseId);
  }

  @Transactional
  public LineItem addProductToPurchase(long purchaseId, long productId) {
    Purchase purchase = getPurchaseById(purchaseId);
    Product product = productService.getProductById(productId);

    LineItem lineItem = new LineItem();
    lineItem.setProduct(product);
    lineItem.setPurchase(purchase);
    entityManager.persist(lineItem);

    purchase.getLineItems().add(lineItem);
    recalculate(purchase);
    entityManager.merge(purchase);
    return lineItem;
  }

  @PostConstruct
  public void setup() throws Throwable {
    Assert.notNull(this.customerService, "'customerService' instance can't be null");
    Assert.notNull(this.productService, "'productService' instance can't be null");
  }

  @Autowired
  public void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Autowired
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
}
