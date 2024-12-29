package rental.model.exception;

import rental.model.customer.CustomerId;

public class CustomerNotFoundException extends AbstractNotFoundException {
    public CustomerNotFoundException(CustomerId id) {
        super("Customer not found with id: " + id.value());
    }
}
