package rental.model.exception;

import rental.model.car.CarId;

public class CarNotFoundException extends AbstractNotFoundException {

    public CarNotFoundException(CarId carId) {
        super("Car not found with id: " + carId.value());
    }
}