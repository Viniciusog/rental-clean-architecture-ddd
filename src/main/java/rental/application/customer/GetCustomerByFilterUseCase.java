package rental.application.customer;

import rental.model.customer.Customer;
import rental.model.customer.CustomerFilter;
import rental.model.customer.CustomerRepository;

import java.util.List;

public class GetCustomerByFilterUseCase {

    private CustomerRepository repository;

    public GetCustomerByFilterUseCase(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> execute(CustomerFilter filter) {
        return repository.getByFilter(filter);
    }
}