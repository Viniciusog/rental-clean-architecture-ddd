package rental.application.customer;

import rental.application.AppTransaction;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerNotFoundException;

public class ActivateCustomerUseCase {

    private AppTransaction transaction;
    private CustomerRepository repository;

    public ActivateCustomerUseCase(AppTransaction transaction, CustomerRepository repository) {
        this.transaction = transaction;
        this.repository  = repository;
    }

    public void execute(CustomerId id) {
        transaction.execute(() -> {
            Customer customer = repository.getById(id).orElseThrow(() -> new CustomerNotFoundException(id));
            customer.activate();
            repository.save(customer);
        });
    }
}
