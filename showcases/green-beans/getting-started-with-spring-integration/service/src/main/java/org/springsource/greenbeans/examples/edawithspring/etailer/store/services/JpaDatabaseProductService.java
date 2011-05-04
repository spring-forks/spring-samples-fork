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

/**
 * @author Josh Long
 */

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.greenbeans.examples.edawithspring.etailer.store.domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class JpaDatabaseProductService implements ProductService {

  @PersistenceContext
  private EntityManager entityManager;

  public Product getProductById(long id) {
    return this.entityManager.find(Product.class, id);
  }

  @Transactional
  public Product createProduct(String title, String desc, double price) {
    Product product = new Product();
    product.setDescription(desc);
    product.setName(title);
    product.setPrice(price);
    this.entityManager.persist(product);
    this.entityManager.refresh(product);
    return product;
  }
}
