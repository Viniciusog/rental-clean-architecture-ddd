package rental.application.rental;

import rental.model.customer.CustomerId;
import rental.model.rental.Rental;
import rental.model.rental.RentalRepository;

import java.util.List;

public class GetRentalsByCustomerIdUseCase {

    private RentalRepository repository;

    public GetRentalsByCustomerIdUseCase(RentalRepository repository) {
        this.repository = repository;
    }

    public List<Rental> execute(CustomerId customerId) {
        return repository.getByCustomerId(customerId);
    }
}