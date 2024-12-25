package rental.model.exception;

import rental.model.customer.CustomerId;

public class CustomerIsAlreadyActiveException extends IllegalStateException {

    public CustomerIsAlreadyActiveException(CustomerId id) {
        super("Customer is already active: " + id.value());
    }
}