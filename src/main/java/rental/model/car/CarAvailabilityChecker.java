package rental.model.car;

import rental.model.rental.DateTimeRange;
import rental.model.rental.RentalRepository;

public class CarAvailabilityChecker {

    private RentalRepository repository;

    public CarAvailabilityChecker(RentalRepository repository) {
        this.repository = repository;
    }

    public boolean isCarAvailable(CarId carId, DateTimeRange timeRange) {
        return repository.getByCarIdAndDateInterval(carId, timeRange).isEmpty();
    }
}