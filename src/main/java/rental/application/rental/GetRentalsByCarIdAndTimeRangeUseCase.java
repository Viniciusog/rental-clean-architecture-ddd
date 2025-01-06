package rental.application.rental;

import rental.model.rental.Rental;
import rental.model.rental.RentalRepository;

import java.util.List;

public class GetRentalsByCarIdAndTimeRangeUseCase {

    private RentalRepository repository;

    public GetRentalsByCarIdAndTimeRangeUseCase(RentalRepository repository) {
        this.repository = repository;
    }

    public List<Rental> execute() {
        return List.of();
    }
}