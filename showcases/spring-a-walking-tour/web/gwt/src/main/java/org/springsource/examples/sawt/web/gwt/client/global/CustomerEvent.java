package org.springsource.examples.sawt.web.gwt.client.global;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import org.springsource.examples.sawt.web.gwt.client.entities.CustomerDto;

/**
 * signal changes to a customer
 */
public class CustomerEvent extends GwtEvent<CustomerEventHandler> {

    public static enum CustomerEventType {CREATED, UPDATED, DELETED}

    public static Type<CustomerEventHandler> TYPE = new Type<CustomerEventHandler>();
    private CustomerEventType customerEventType;
    private CustomerDto customerDto;
                public CustomerDto getCustomerDto(){return this.customerDto ;}

    public CustomerEventType getCustomerEventType() {
        return customerEventType;
    }



    public CustomerEvent(CustomerDto customerDto, CustomerEventType customerEventType) {
        super();
        this.customerDto = customerDto;
        this.customerEventType = customerEventType;
    }

    @Override
    public Type<CustomerEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CustomerEventHandler customerEventHandler) {
        customerEventHandler.onCustomerUpdated(this);
    }
}
