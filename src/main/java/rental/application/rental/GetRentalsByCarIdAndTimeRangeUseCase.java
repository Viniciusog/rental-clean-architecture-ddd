package rental.application.rental;

import rental.model.car.CarId;
import rental.model.rental.DateTimeRange;
import rental.model.rental.Rental;
import rental.model.rental.RentalRepository;

import java.util.List;

public class GetRentalsByCarIdAndTimeRangeUseCase {

    private RentalRepository repository;

    public GetRentalsByCarIdAndTimeRangeUseCase(RentalRepository repository) {
        this.repository = repository;
    }

    public List<Rental> execute(CarId carId, DateTimeRange timeRange) {
        return repository.getByCarIdAndTimeRange(carId, timeRange);
    }
}