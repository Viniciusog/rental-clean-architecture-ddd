package rental.application.customer;

import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerNotFoundException;

public class GetByIdCustomerUseCase {

    private CustomerRepository repository;

    public GetByIdCustomerUseCase(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer execute(CustomerId id) {
        return repository.getById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}