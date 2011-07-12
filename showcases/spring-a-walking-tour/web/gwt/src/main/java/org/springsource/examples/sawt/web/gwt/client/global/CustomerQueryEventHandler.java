package org.springsource.examples.sawt.web.gwt.client.global;

import com.google.gwt.event.shared.EventHandler;

public interface CustomerQueryEventHandler extends EventHandler {
    void onCustomerQueried (long customerId);
}
