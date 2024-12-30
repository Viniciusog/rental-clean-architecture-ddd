package rental.application.customer;

import rental.application.AppTransaction;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerRepository;

public class DeleteCustomerUseCase {

    private AppTransaction transaction;
    private CustomerRepository repository;

    public DeleteCustomerUseCase(AppTransaction transaction, CustomerRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public void execute(CustomerId id) {
        transaction.execute(() -> {
            repository.delete(id);
        });
    }
}