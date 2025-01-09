package rental.model.car;

import rental.Validation;
import rental.model.exception.CarNotAvailableException;
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
        return !repository.existsByCarIdAndTimeRange(carId, timeRange);
    }

    public void carIsAvailableOrThrowException(CarId carId, DateTimeRange timeRange) {
        Validation.required(carId, "carId is required");
        Validation.required(timeRange, "timeRange is required");
        if (!isCarAvailable(carId, timeRange)) {
            throw new CarNotAvailableException(carId, timeRange);
        }
    }
}