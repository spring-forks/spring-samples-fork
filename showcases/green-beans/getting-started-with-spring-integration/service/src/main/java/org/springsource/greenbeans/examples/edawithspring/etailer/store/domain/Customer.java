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
@Table(name = "customer")
public class Customer   {

  private Long id;
  private String firstName;
  private String lastName;
  private Set<Purchase> purchases = new HashSet<Purchase>(0);

  public Customer() {
  }

  public Customer(String fn, String ln) {
    this.firstName = fn;
    this.lastName = ln;
  }

  public Customer(long id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Customer(long id, String firstName, String lastName, Set<Purchase> purchases) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.purchases = purchases;
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

  @Column(name = "first_name", nullable = false)
  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "last_name", nullable = false)
  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
  public Set<Purchase> getPurchases() {
    return this.purchases;
  }

  public void setPurchases(Set<Purchase> purchases) {
    this.purchases = purchases;
  }
}


