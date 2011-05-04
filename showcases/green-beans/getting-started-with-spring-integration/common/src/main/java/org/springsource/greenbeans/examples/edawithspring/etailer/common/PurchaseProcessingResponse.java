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
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "purchase-processing-response", namespace = CommonConstants.SHIPPING_NAMESPACE)
public class PurchaseProcessingResponse {

  private long customerId;
  private long purchaseId;
  private String requestConfirmationId;
  private Date confirmationDate;

  public PurchaseProcessingResponse() {
  }

  public PurchaseProcessingResponse(long customerId, long purchaseId, Date confirmationDate) {
    this.customerId = customerId;
    this.purchaseId = purchaseId;
    this.confirmationDate = confirmationDate;
    this.requestConfirmationId = this.customerId + "_" + this.purchaseId;
  }

  @XmlAttribute(name = "customer-id", required = true)
  public long getCustomerId() {
    return customerId;
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

  @XmlAttribute(name = "request-confirmation-id", required = true)
  public String getRequestConfirmationId() {
    return requestConfirmationId;
  }

  public void setRequestConfirmationId(String requestConfirmationId) {
    this.requestConfirmationId = requestConfirmationId;
  }

  @XmlAttribute(name = "confirmation-date", required = true)
  public Date getConfirmationDate() {
    return confirmationDate;
  }

  public void setConfirmationDate(Date confirmationDate) {
    this.confirmationDate = confirmationDate;
  }
}
