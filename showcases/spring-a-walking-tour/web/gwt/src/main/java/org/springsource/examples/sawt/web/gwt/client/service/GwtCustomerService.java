package org.springsource.examples.sawt.web.gwt.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.springsource.examples.sawt.web.gwt.client.entities.CustomerDto;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("crm")
public interface GwtCustomerService extends RemoteService {

    CustomerDto getCustomerById(long customerId);

}
