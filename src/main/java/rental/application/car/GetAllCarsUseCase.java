package rental.application.car;

import rental.model.car.Car;
import rental.model.car.CarRepository;

import java.util.List;

public class GetAllCarsUseCase {

    private CarRepository repository;

    public GetAllCarsUseCase(CarRepository repository) {
        this.repository = repository;
    }

    public List<Car> execute() {
        return repository.getAll();
    }
}