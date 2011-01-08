package org.springsource.examples.crm.services.jpa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springsource.examples.crm.model.Product;
import org.springsource.examples.crm.services.ProductService;

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
