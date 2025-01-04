package rental.model.car;

import rental.Validation;
import rental.model.rental.DateTimeRange;
import rental.model.rental.RentalRepository;

public class CarAvailabilityChecker {

    private RentalRepository repository;

    public CarAvailabilityChecker(RentalRepository repository) {
        this.repository = repository;
    }

    public boolean isCarAvailable(CarId carId, DateTimeRange timeRange) {
        Validation.required(carId, "carId is required");
        Validation.required(timeRange, "timeRange is required");
        return repository.getByCarIdAndDateInterval(carId, timeRange).isEmpty();
    }
}