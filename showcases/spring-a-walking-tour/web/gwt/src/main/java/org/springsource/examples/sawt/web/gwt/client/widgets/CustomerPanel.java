package org.springsource.examples.sawt.web.gwt.client.widgets;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.springsource.examples.sawt.web.gwt.client.entities.CustomerDto;

// todo publish events that require the use of the services using the GWT EventBus. See http://www.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html
// todo CustomerPanel shouldn't know about the CustomerService, specifically. it should be natural to publish 'customerCreatedEvent' and 'customerUpdatedEvents' and respond to them, appropriately.

public class CustomerPanel extends Composite {

    interface CustomerUiBinder extends UiBinder<Widget, CustomerPanel> {
    }

    private static CustomerUiBinder uiBinder = GWT.create(CustomerUiBinder.class);

    private CustomerDto customerDto;

    @UiField
    SpanElement customerId;

    @UiField
    TextBox firstName;

    @UiField
    TextBox lastName;

    @UiField
    Button updateButton;

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
        renderCustomer(customerDto);
    }

    private void renderCustomer(CustomerDto customerDto) {

        // enable the widgets
        FocusWidget[] widgets = {firstName, lastName, updateButton};
        for (FocusWidget focusWidget : widgets)
            focusWidget.setEnabled(true);

        firstName.setText(customerDto.getFirstName());
        lastName.setText(customerDto.getLastName());
        customerId.setInnerText(customerDto.getId() + "");

    }


    public CustomerPanel() {
        initWidget(uiBinder.createAndBindUi(this));

        FocusWidget[] widgets = {firstName, lastName, updateButton};
        for (FocusWidget focusWidget : widgets)
            focusWidget.setEnabled(false);

        updateButton.addClickHandler(  clickHandler  );

    }

    private  ClickHandler clickHandler = new ClickHandler() {
        public void onClick(ClickEvent clickEvent) {
             Window.alert("so you want to submit the updated customer Information to the server, eh?");
        }
    };
}