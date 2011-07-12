package org.springsource.examples.sawt.web.gwt.client.global;

import com.google.gwt.event.shared.EventHandler;

public interface CustomerEventHandler extends EventHandler {
    void onCustomerUpdated(CustomerEvent cEvt);
}