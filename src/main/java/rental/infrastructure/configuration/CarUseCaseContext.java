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
    private CarRepository repository;

    @Autowired
    private RentalRepository rentalRepository;

    @Bean
    public CreateCarUseCase createCarUseCase() {
        return new CreateCarUseCase(transaction, repository);
    }

    @Bean
    public GetCarByIdUseCase getCarByIdUseCase() { return new GetCarByIdUseCase(repository); }

    @Bean
    public DeleteCarUseCase deleteCarUseCase() {
        return new DeleteCarUseCase(transaction, repository);
    }

    @Bean
    public UpdateCarUseCase updateCarUseCase() {
        return new UpdateCarUseCase(transaction, repository);
    }

    @Bean
    public CarAvailabilityChecker getCarAvailabilityChecker() {
        return new CarAvailabilityChecker(rentalRepository);
    }

    @Bean
    public GetCarAvailabilityUseCase getCarAvailabilityUseCase() {
        return new GetCarAvailabilityUseCase(new CarAvailabilityChecker(rentalRepository));
    }
}