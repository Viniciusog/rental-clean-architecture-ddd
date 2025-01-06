package rental.application.customer;

import rental.application.AppTransaction;
import rental.model.Email;
import rental.model.customer.Customer;
import rental.model.customer.CustomerName;
import rental.model.customer.CustomerRepository;

public class CreateCustomerUseCase {

    private AppTransaction transaction;
    private CustomerRepository repository;

    public CreateCustomerUseCase(AppTransaction transaction, CustomerRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public void execute(CustomerName name, Email email) {
        transaction.execute(() -> {
            repository.save(Customer.builder().name(name).email(email).build());
        });
    }
}