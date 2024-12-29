package rental.model.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);
    void delete(CustomerId customerId);
    Optional<Customer> getById(CustomerId customerId);
    List<Customer> getByFilter(CustomerFilter filter);
}