package org.springsource.examples.sawt.web.gwt.client.global;

import com.google.gwt.event.shared.GwtEvent;

public class CustomerQueryEvent extends GwtEvent<CustomerQueryEventHandler> {

    public static Type<CustomerQueryEventHandler> TYPE = new Type<CustomerQueryEventHandler>();

    private long customerId = -1;

    public CustomerQueryEvent(long cid) {
        super();
        this.customerId = cid;
    }

    public CustomerQueryEvent(String customerId) {
        super();
        this.customerId = Long.parseLong(customerId);
    }

    @Override
    public Type<CustomerQueryEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CustomerQueryEventHandler c) {
        if (this.customerId != -1)
            c.onCustomerQueried(this.customerId);
    }
}
