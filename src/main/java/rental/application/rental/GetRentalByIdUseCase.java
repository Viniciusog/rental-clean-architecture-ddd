package rental.application.rental;

import rental.model.exception.RentalNotFoundException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

public class GetRentalByIdUseCase {

    private RentalRepository repository;

    public GetRentalByIdUseCase(RentalRepository repository) {
        this.repository = repository;
    }

    public Rental execute(RentalId rentalId) {
        return repository.getById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException(rentalId));
    }
}