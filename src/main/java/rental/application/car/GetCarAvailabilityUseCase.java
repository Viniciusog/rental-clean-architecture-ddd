package rental.application.car;

import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarId;
import rental.model.rental.DateTimeRange;

public class GetCarAvailabilityUseCase {

    private CarAvailabilityChecker carAvailabilityChecker;

    public GetCarAvailabilityUseCase(CarAvailabilityChecker carAvailabilityChecker) {
        this.carAvailabilityChecker = carAvailabilityChecker;
    }

    public boolean execute(CarId carId, DateTimeRange timeRange) {

        return carAvailabilityChecker.isCarAvailable(carId, timeRange);
    }
}
