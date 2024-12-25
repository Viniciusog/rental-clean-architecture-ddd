package rental.model.exception;

import rental.model.customer.CustomerId;

public class CustomerIsAlreadyInactiveException extends IllegalStateException {

    public CustomerIsAlreadyInactiveException(CustomerId id) {
        super("Customer is already inactive: " + id);
    }
}
