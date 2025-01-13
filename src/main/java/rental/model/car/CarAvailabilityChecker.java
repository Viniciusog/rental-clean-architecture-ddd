package rental.model.car;

import rental.Validation;
import rental.model.exception.CarNotAvailableException;
import rental.model.exception.CarNotFoundException;
import rental.model.rental.DateTimeRange;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

public class CarAvailabilityChecker {

    private RentalRepository rentalRepository;
    private CarRepository carRepository;

    public CarAvailabilityChecker(RentalRepository rentalRepository, CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
    }

    public boolean isCarAvailable(CarId carId, DateTimeRange timeRange) {
        Validation.required(carId, "carId is required");
        Validation.required(timeRange, "timeRange is required");
        if (carRepository.getById(carId).isEmpty()) {
            throw new CarNotFoundException(carId);
        }
        return !rentalRepository.existsByCarIdAndTimeRange(carId, timeRange);
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

    public void ensureCarIsAvailableWithRentalExclusionOrThrowException(CarId carId, DateTimeRange timeRange, RentalId rentalId) {
        Validation.required(carId, "carId is required");
        Validation.required(timeRange, "timeRange is required");
        Validation.required(rentalId, "rentalId is required");

        if (carRepository.getById(carId).isEmpty()) {
            throw new CarNotFoundException(carId);
        }

        if (rentalRepository.existsByCarIdAndTimeRangeWithRentalExclusion(carId, timeRange, rentalId)) {
            throw new CarNotAvailableException(carId, timeRange);
        }
    }
}