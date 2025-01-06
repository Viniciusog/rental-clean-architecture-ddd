package rental.model.exception;

import rental.model.car.CarId;
import rental.model.rental.DateTimeRange;

public class CarNotAvailableException extends AbstractConflictException {
    public CarNotAvailableException(CarId carId, DateTimeRange timeRange) {
        super(String.format("Car with id: %s is not available for the given time range: %s to %s",
                carId.value(), timeRange.start(), timeRange.end()));
    }
}
