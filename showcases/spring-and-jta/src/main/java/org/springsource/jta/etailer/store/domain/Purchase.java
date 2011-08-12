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
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purchase")
public class Purchase implements Serializable {


  private Set<LineItem> lineItems = new HashSet<LineItem>(0);

  private Long id;

  private Customer customer;

  private boolean frozen;

  private double total;

  public Purchase() {
  }

  public Purchase(long id, Customer customer, double total) {
    this.id = id;
    this.customer = customer;
    this.total = total;
  }

  public Purchase(long id, Customer customer, double total, Set<LineItem> lineItems) {
    this.id = id;
    this.customer = customer;
    this.total = total;
    this.lineItems = lineItems;
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

  public boolean isFrozen() {
    return frozen;
  }

  public void setFrozen(boolean frozen) {
    this.frozen = frozen;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  public Customer getCustomer() {
    return this.customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @Column(name = "total", nullable = false, precision = 17, scale = 17)
  public double getTotal() {
    return this.total;
  }

  public void setTotal(double total) {
    this.total = total;
  }
  public void setLineItems(Set<LineItem> lineItems) {
    this.lineItems = lineItems;
  }


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "purchase")
	public Set<LineItem> getLineItems() {
		return this.lineItems;
	}
}


