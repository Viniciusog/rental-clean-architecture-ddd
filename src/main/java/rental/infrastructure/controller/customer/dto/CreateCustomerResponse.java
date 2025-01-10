package rental.infrastructure.controller.customer.dto;

import rental.model.customer.CustomerId;

public record CreateCustomerResponse(Long id) {
    public static CreateCustomerResponse of(CustomerId customerId) {
        return new CreateCustomerResponse(customerId.value());
    }
}
