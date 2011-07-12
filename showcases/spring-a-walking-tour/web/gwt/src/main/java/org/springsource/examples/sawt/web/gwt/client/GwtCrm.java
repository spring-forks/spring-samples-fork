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
    //http://stackoverflow.com/questions/6030202/how-to-use-the-gwt-eventbus
    public static EventBus eventBus = GWT.create(SimpleEventBus.class);

    private final GwtCustomerServiceAsync crmService = GWT.create(GwtCustomerService.class);

    private final Messages messages = GWT.create(Messages.class);

    void updateCustomer(final CustomerDto customerDto) {
        crmService.updateCustomer(customerDto.getId(), customerDto.getFirstName(), customerDto.getLastName(), new AsyncCallback<Void>() {
            public void onFailure(Throwable throwable) {
            }

            public void onSuccess(Void aVoid) {
            }
        });
    }

    public void onModuleLoad() {
        final CustomerPanel customerPanel = new CustomerPanel(eventBus, messages);

        RootPanel.get("customerPanelContainer").add(customerPanel);
        eventBus.addHandler(CustomerQueryEvent.TYPE, new CustomerQueryEventHandler() {
            public void onCustomerQueried(final long customerId) {
                crmService.getCustomerById(customerId, new AsyncCallback<CustomerDto>() {

                    public void onFailure(Throwable throwable) {
                        Window.alert("Problem! Could not retrieve the customer " + customerId +
                                "; the error is " + throwable.toString());
                    }

                    public void onSuccess(CustomerDto customerDto) {
                        customerPanel.setCustomerDto(customerDto);
                    }
                });
            }
        });
        eventBus.addHandler(CustomerEvent.TYPE, new CustomerEventHandler() {
            public void onCustomerUpdated(CustomerEvent cEvt) {
                if (cEvt.getCustomerEventType().equals(CustomerEvent.CustomerEventType.UPDATED)) {
                    updateCustomer(cEvt.getCustomerDto());
                }
            }
        });


    }
}
