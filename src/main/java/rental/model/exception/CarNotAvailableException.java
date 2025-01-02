package rental.model.exception;

import rental.model.car.CarId;

public class CarNotAvailableException extends AbstractConflictException {
    public CarNotAvailableException(CarId carId) {
        super("Car with id: " + carId.value() + " is not available to rent.");
    }
}
