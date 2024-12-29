package rental.infrastructure.repository.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import rental.infrastructure.repository.car.JpaCarRepository;
import rental.model.car.CarId;
import rental.model.customer.Customer;
import rental.model.customer.CustomerFilter;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepositoryAdapter implements CustomerRepository {

    @Autowired
    private JpaCustomerRepository repository;

    @Override
    public void save(Customer customer) {
        CustomerEntity entity = new CustomerEntity(customer);

        repository.save(entity);
        customer.created(CustomerId.of(entity.id()));
    }

    @Override
    public void delete(CustomerId customerId) {
        if (!repository.existsById(customerId.value())) {
            throw new CustomerNotFoundException(customerId);
        }
        repository.deleteById(customerId.value());
    }

    @Override
    public Optional<Customer> getById(CustomerId customerId) {
        return repository.findById(customerId.value()).map(CustomerEntity::toCustomer);
    }

    @Override
    public List<Customer> getByFilter(CustomerFilter filter) {
        return repository.findByFilter(filter.nameStarting(), filter.nameContaining())
                .stream().map(CustomerEntity::toCustomer).toList();
    }
}