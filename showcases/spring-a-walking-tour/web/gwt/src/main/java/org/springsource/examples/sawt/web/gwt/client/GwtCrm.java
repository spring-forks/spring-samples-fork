package org.springsource.examples.sawt.web.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import org.springsource.examples.sawt.web.gwt.client.entities.CustomerDto;
import org.springsource.examples.sawt.web.gwt.client.global.CustomerEvent;
import org.springsource.examples.sawt.web.gwt.client.global.CustomerEventHandler;
import org.springsource.examples.sawt.web.gwt.client.global.CustomerQueryEvent;
import org.springsource.examples.sawt.web.gwt.client.global.CustomerQueryEventHandler;
import org.springsource.examples.sawt.web.gwt.client.service.GwtCustomerService;
import org.springsource.examples.sawt.web.gwt.client.service.GwtCustomerServiceAsync;
import org.springsource.examples.sawt.web.gwt.client.widgets.CustomerPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtCrm implements EntryPoint {

    private final GwtCustomerServiceAsync crmService = GWT.create(GwtCustomerService.class);

    private final Messages messages = GWT.create(Messages.class);

    public void onModuleLoad() {
        final CustomerPanel customerPanel = new CustomerPanel(crmService, messages);
        RootPanel.get("customerPanelContainer").add(customerPanel);
    }
}
