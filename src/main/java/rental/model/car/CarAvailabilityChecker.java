package rental.model.car;

import rental.Validation;
import rental.model.exception.CarNotAvailableException;
import rental.model.exception.CarNotFoundException;
import rental.model.rental.DateTimeRange;
import rental.model.rental.RentalRepository;

public class CarAvailabilityChecker {

    private RentalRepository repository;
    private CarRepository carRepository;

    public CarAvailabilityChecker(RentalRepository repository, CarRepository carRepository) {
        this.repository = repository;
        this.carRepository = carRepository;
    }

    public boolean isCarAvailable(CarId carId, DateTimeRange timeRange) {
        Validation.required(carId, "carId is required");
        Validation.required(timeRange, "timeRange is required");
        if (carRepository.getById(carId).isEmpty()) {
            throw new CarNotFoundException(carId);
        }
        return !repository.existsByCarIdAndTimeRange(carId, timeRange);
    }

    public void ensureCarIsAvailableOrThrowException(CarId carId, DateTimeRange timeRange) {
        Validation.required(carId, "carId is required");
        Validation.required(timeRange, "timeRange is required");

        if (carRepository.getById(carId).isEmpty()) {
            throw new CarNotFoundException(carId);
        }

        if (!isCarAvailable(carId, timeRange)) {
            throw new CarNotAvailableException(carId, timeRange);
        }
    }
}