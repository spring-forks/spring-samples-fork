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
package org.springsource.greenbeans.examples.edawithspring.etailer.common;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "line-item", propOrder = {"id", "productId", "fulfillmentDate"}, namespace = CommonConstants.SHIPPING_NAMESPACE)
public class PurchaseLineItem {
  private long id;
  private Date fulfillmentDate;
  private long productId;

  @XmlAttribute(name = "id", required = true)
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @XmlAttribute(name = "fulfillment-date", required = false)
  public Date getFulfillmentDate() {
    return fulfillmentDate;
  }

  public void setFulfillmentDate(Date fulfillmentDate) {
    this.fulfillmentDate = fulfillmentDate;
  }

  @XmlAttribute(name = "product-id", required = true)
  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }
}
