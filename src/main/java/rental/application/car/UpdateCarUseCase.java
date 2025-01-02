package rental.application.car;

import rental.application.AppTransaction;
import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.CarRepository;
import rental.model.car.Model;
import rental.model.exception.CarNotFoundException;

import java.math.BigDecimal;
import java.time.Year;

public class UpdateCarUseCase {

    private AppTransaction transaction;
    private CarRepository repository;

    public UpdateCarUseCase(AppTransaction transaction, CarRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public void execute(CarId carId, Model model, Year year, BigDecimal dailyPrice) {
       transaction.execute(() -> {
           Car car = repository.getById(carId).orElseThrow(() -> new CarNotFoundException(carId));

           car.update(model, year, dailyPrice);
           repository.save(car);
       });
    }
}
