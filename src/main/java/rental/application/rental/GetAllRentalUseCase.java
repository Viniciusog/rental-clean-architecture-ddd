package rental.application.rental;

import rental.model.rental.Rental;
import rental.model.rental.RentalRepository;

import java.util.List;

public class GetAllRentalUseCase {

    private RentalRepository repository;

    public GetAllRentalUseCase(RentalRepository repository) {
        this.repository = repository;
    }

    public List<Rental> execute() {
        return repository.getAll();
    }
}