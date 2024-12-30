package rental.application.car;

import rental.application.AppTransaction;
import rental.model.car.CarId;
import rental.model.car.CarRepository;

public class DeleteCarUseCase {

    private AppTransaction transaction;
    private CarRepository repository;

    public DeleteCarUseCase(AppTransaction transaction, CarRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public void execute(CarId carId) {
        transaction.execute(() -> {
            repository.deleteById(carId);
        });
    }
}