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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * this class contains the minimum information required to travel to different systems.
 * It is designed to be marhsalled using JAXB.
 */
@XmlRootElement(name = "purchase-processing-request", namespace = CommonConstants.SHIPPING_NAMESPACE)
public class PurchaseProcessingRequest {

  private Set<PurchaseLineItem> purchaseLineItems = new HashSet<PurchaseLineItem>();

  private long customerId;
  private long purchaseId;

  private Date shipped;

  @XmlAttribute(name = "customer-id", required = true)
  public long getCustomerId() {
    return customerId;
  }

  public void addLineItem(PurchaseLineItem lineItem) {
    this.purchaseLineItems.add(lineItem);
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  @XmlAttribute(name = "purchase-id", required = true)
  public long getPurchaseId() {
    return purchaseId;
  }

  public void setPurchaseId(long purchaseId) {
    this.purchaseId = purchaseId;
  }

  @XmlAttribute(name = "shipped", required = false)
  public Date getShipped() {
    return shipped;
  }

  public void setShipped(Date shipped) {
    this.shipped = shipped;
  }

  @XmlElementWrapper(name = "purchase-line-items" ,namespace = CommonConstants.SHIPPING_NAMESPACE)
  @XmlElement(name = "purchase-line-item", required = true, nillable = false ,namespace = CommonConstants.SHIPPING_NAMESPACE)
  public Set<PurchaseLineItem> getPurchaseLineItems() {
    return purchaseLineItems;
  }

  public void setPurchaseLineItems(Set<PurchaseLineItem> purchaseLineItems) {
    this.purchaseLineItems = purchaseLineItems;
  }
}
