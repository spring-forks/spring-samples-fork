package org.springsource.examples.sawt.web.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springsource.examples.sawt.CustomerService;
import org.springsource.examples.sawt.services.model.Customer;
import org.springsource.examples.sawt.web.gwt.client.entities.CustomerDto;
import org.springsource.examples.sawt.web.gwt.client.service.GwtCustomerService;

@SuppressWarnings("serial,unchecked")
public class GwtCustomerServiceImpl extends RemoteServiceServlet implements GwtCustomerService {

    private <T> T beanOfType(Class t) {
        ApplicationContext applicationContext =WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        return (T)  applicationContext.getBean(t);
    }

    public CustomerDto getCustomerById(long customerId) {
        try {
            CustomerService customerService = beanOfType(CustomerService.class);
            Customer customer = customerService.getCustomerById(customerId);
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customerDto, customer);
            return customerDto;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
