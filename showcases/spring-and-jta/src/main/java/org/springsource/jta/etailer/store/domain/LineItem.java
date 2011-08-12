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
package org.springsource.jta.etailer.store.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "line_item")
public class LineItem  implements Serializable {

  private Long id;
  private Purchase purchase;
  private Product product;
  private Date shipped, purchased;

  @Column(name = "purchased", nullable = true)
  public Date getPurchasedDate() {
    return purchased;
  }

  public void setPurchasedDate(Date purchased) {
    this.purchased = purchased;
  }

  @Transient
  public boolean hasBeenPurchased() {
    return this.purchased != null;
  }

  @Transient
  public boolean hasShipped() {
    return this.shipped != null;
  }

  @Column(name = "shipped", nullable = true)
  public Date getShippedDate() {
    return shipped;
  }

  public void setShippedDate(Date shippedDate) {
    this.shipped = shippedDate;
  }

  public LineItem() {
  }

  public LineItem(long id, Purchase purchase, Product product) {
    this.id = id;
    this.purchase = purchase;
    this.product = product;
  }

  @Id
  @GeneratedValue
  @Column(name = "id", unique = true, nullable = false)
  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "purchase_id", nullable = false)
  public Purchase getPurchase() {
    return this.purchase;
  }

  public void setPurchase(Purchase purchase) {
    this.purchase = purchase;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  public Product getProduct() {
    return this.product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}


