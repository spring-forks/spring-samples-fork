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

package org.springsource.greenbeans.examples.edawithspring.etailer.store.services.partnernotifications;

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;
import org.springsource.greenbeans.examples.edawithspring.etailer.common.PurchaseLineItem;
import org.springsource.greenbeans.examples.edawithspring.etailer.common.PurchaseProcessingRequest;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.LineItem;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Purchase;

@SuppressWarnings("unused")
@Component("purchaseRequestTransformer")
public class PurchaseToPurchaseProcessingRequestTransformer {

  @Transformer
  public PurchaseProcessingRequest fromPurchase(Purchase purchase) {
    PurchaseProcessingRequest request1 = new PurchaseProcessingRequest();
    request1.setCustomerId(purchase.getCustomer().getId());
    request1.setPurchaseId(purchase.getId());
    for (LineItem li : purchase.getLineItems()) {
      PurchaseLineItem lineItem = new PurchaseLineItem();
      lineItem.setProductId(li.getProduct().getId());
      lineItem.setId(li.getId());
      request1.addLineItem(lineItem);
    }
    return request1;
  }
}
