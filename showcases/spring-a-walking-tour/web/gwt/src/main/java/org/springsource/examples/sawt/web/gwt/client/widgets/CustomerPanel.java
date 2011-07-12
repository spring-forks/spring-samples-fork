package org.springsource.examples.sawt.web.gwt.client.widgets;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.springsource.examples.sawt.web.gwt.client.Messages;
import org.springsource.examples.sawt.web.gwt.client.entities.CustomerDto;
import org.springsource.examples.sawt.web.gwt.client.global.CustomerEvent;
import org.springsource.examples.sawt.web.gwt.client.global.CustomerQueryEvent;

// todo publish events that require the use of the services using the GWT EventBus. See http://www.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html
// todo CustomerPanel shouldn't know about the CustomerService, specifically. it should be natural to publish 'customerCreatedEvent' and 'customerUpdatedEvents' and respond to them, appropriately.

public class CustomerPanel extends Composite {

    interface CustomerUiBinder extends UiBinder<Widget, CustomerPanel> {
    }

    private EventBus eventBus;
    private CustomerUiBinder uiBinder = GWT.create(CustomerUiBinder.class);
    private CustomerDto customerDto;

    @UiField
    TextBox firstName;
    @UiField
    TextBox lastName;
    @UiField
    Button updateButton;
    @UiField
    Label customerIdLabel;
    @UiField
    Label firstNameLabel;
    @UiField
    Label lastNameLabel;
    @UiField
    Button searchCustomerButton;
    @UiField
    TextBox customerId;
    @UiField
    Button cancelButton;

    public void setCustomerDto(CustomerDto customerDto) {

        this.customerDto = customerDto;
        if (customerDto != null) {
            firstName.setText(customerDto.getFirstName());
            lastName.setText(customerDto.getLastName());
        } else {
            firstName.setText("");
            lastName.setText("");
        }
        enableEditor();
    }

    private void setEditorEnabled(boolean enabled) {

        searchCustomerButton.setEnabled(!enabled);
        customerId.setEnabled(!enabled);

        FocusWidget[] widgets = {firstName, lastName, updateButton, cancelButton};
        for (FocusWidget focusWidget : widgets)
            focusWidget.setEnabled(enabled);
    }

    private void enableEditor() {
        setEditorEnabled(true);
    }

    private void disableEditor() {
        setEditorEnabled(false);
    }

    public CustomerPanel(EventBus eventBus, Messages messages) {
        this.eventBus = eventBus;

        initWidget(uiBinder.createAndBindUi(this));

        updateButton.setText(messages.updateCustomer());
        firstNameLabel.setText(messages.firstName());
        lastNameLabel.setText(messages.lastName());
        customerIdLabel.setText(messages.customerId());
        searchCustomerButton.setText(messages.searchCustomerById());
        cancelButton.setText(messages.cancel());


        updateButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                updateCustomer();
            }
        });
        searchCustomerButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                requestCustomerRecord(customerId.getText());
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                setCustomerDto(null);
                disableEditor();
            }
        });

        disableEditor();
    }


    private void requestCustomerRecord(String customerId) {
        CustomerQueryEvent customerQueryEvent = new CustomerQueryEvent(customerId);
        eventBus.fireEvent(customerQueryEvent);
    }

    private void updateCustomer() {
        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setFirstName(firstName.getText());
        customerDto1.setLastName(lastName.getText());
        customerDto1.setId(customerDto.getId());

        CustomerEvent customerEvent = new CustomerEvent(customerDto1, CustomerEvent.CustomerEventType.UPDATED);

        eventBus.fireEvent(customerEvent);
        setCustomerDto(null);
        disableEditor();
    }
}