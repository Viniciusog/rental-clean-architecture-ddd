package rental.application.customer;

import rental.application.AppTransaction;
import rental.model.Email;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerName;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerNotFoundException;

public class UpdateCustomerUseCase {

    private AppTransaction transaction;
    private CustomerRepository repository;

    public UpdateCustomerUseCase(AppTransaction transaction, CustomerRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public void execute(CustomerId id, CustomerName name, Email email) {
        Customer customer = repository.getById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        transaction.execute(() -> {
            customer.update(name, email);
            repository.save(customer);
        });
    }
}
