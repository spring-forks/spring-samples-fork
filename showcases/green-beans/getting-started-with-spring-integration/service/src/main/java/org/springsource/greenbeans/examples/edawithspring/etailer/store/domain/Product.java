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
package org.springsource.greenbeans.examples.edawithspring.etailer.store.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product   {

  private Long id;
  private String description;
  private String name;
  private double price;
  private Set<LineItem> lineItems = new HashSet<LineItem>(0);

  public Product() {
  }

  public Product(long id, String name, double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Product(long id, String description, String name, double price, Set<LineItem> lineItems) {
    this.id = id;
    this.description = description;
    this.name = name;
    this.price = price;
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

  @Column(name = "description")
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(name = "name", nullable = false)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "price", nullable = false, precision = 17, scale = 17)
  public double getPrice() {
    return this.price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
  public Set<LineItem> getLineItems() {
    return this.lineItems;
  }

  public void setLineItems(Set<LineItem> lineItems) {
    this.lineItems = lineItems;
  }
}


