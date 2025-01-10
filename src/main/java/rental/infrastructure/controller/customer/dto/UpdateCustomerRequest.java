package rental.infrastructure.controller.customer.dto;

import rental.model.Email;
import rental.model.customer.CustomerName;

public record UpdateCustomerRequest(String name, String email) {

    public CustomerName typedName() {
        return CustomerName.of(this.name);
    }

    public Email typedEmail() {
        return Email.of(email);
    }
}