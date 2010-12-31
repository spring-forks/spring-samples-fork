package org.springframework.samples;

/**
 * Decouples the clients from the implementation of this particular service.
 * <p/>
 * They will work with our service in terms of this interface, and nothing else.
 *
 * @author Josh Long
 */
public interface CustomerService {
  Customer getCustomerById(long customerId);
}
