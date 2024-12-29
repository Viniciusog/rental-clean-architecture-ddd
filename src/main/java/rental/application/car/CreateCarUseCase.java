package rental.application.car;

import rental.application.AppTransaction;
import rental.model.car.Car;
import rental.model.car.CarRepository;
import rental.model.car.Model;

import java.time.Year;

public class CreateCarUseCase {

    private final AppTransaction transaction;
    private final CarRepository repository;

    public CreateCarUseCase(AppTransaction transaction, CarRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public void execute(Model model, Year year) {
        Car car = Car.builder()
                .model(model)
                .year(year)
                .build();
        transaction.execute(() -> repository.save(car));
    }
}