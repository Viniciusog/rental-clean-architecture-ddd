package rental.infrastructure.controller.customer.dto;

import rental.model.customer.Customer;

public record CustomerResponse(Long id, String name, String email, Boolean active) {

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(
                customer.id().value(),
                customer.name().value(),
                customer.email().address(),
                customer.isActive());
    }
}