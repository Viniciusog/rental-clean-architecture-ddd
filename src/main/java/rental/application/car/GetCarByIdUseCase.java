package rental.application.car;

import rental.application.AppTransaction;
import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotFoundException;

public class GetCarByIdUseCase {

    private CarRepository repository;

    public GetCarByIdUseCase(CarRepository repository) {
        this.repository  = repository;
    }

    public Car execute(CarId carId) {
        return repository.getById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
    }
}
