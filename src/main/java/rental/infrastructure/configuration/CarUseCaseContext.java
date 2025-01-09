package rental.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rental.application.AppTransaction;
import rental.application.car.*;
import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarRepository;
import rental.model.rental.RentalRepository;

@Configuration
public class CarUseCaseContext {

    @Autowired
    private AppTransaction transaction;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Bean
    public CreateCarUseCase createCarUseCase() {
        return new CreateCarUseCase(transaction, carRepository);
    }

    @Bean
    public GetCarByIdUseCase getCarByIdUseCase() { return new GetCarByIdUseCase(carRepository); }

    @Bean
    public DeleteCarUseCase deleteCarUseCase() {
        return new DeleteCarUseCase(transaction, carRepository);
    }

    @Bean
    public UpdateCarUseCase updateCarUseCase() {
        return new UpdateCarUseCase(transaction, carRepository);
    }

    @Bean
    public CarAvailabilityChecker getCarAvailabilityChecker() {
        return new CarAvailabilityChecker(rentalRepository, carRepository);
    }

    @Bean
    public GetCarAvailabilityUseCase getCarAvailabilityUseCase() {
        return new GetCarAvailabilityUseCase(getCarAvailabilityChecker());
    }

    @Bean
    public GetAllCarsUseCase getAllCarsUseCase() {
        return new GetAllCarsUseCase(carRepository);
    }
}