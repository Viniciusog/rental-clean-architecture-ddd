package rental.application.customer;

import rental.application.AppTransaction;
import rental.model.Email;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerName;
import rental.model.customer.CustomerRepository;

public class CreateCustomerUseCase {

    private AppTransaction transaction;
    private CustomerRepository repository;

    public CreateCustomerUseCase(AppTransaction transaction, CustomerRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public CustomerId execute(CustomerName name, Email email) {
        Customer customer = Customer.builder().name(name).email(email).build();
        transaction.execute(() -> {
            repository.save(customer);
        });
        return customer.id();
    }
}