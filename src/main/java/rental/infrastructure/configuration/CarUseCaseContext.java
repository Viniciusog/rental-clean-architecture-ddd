package rental.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rental.application.AppTransaction;
import rental.application.car.CreateCarUseCase;
import rental.application.car.GetCarByIdUseCase;
import rental.model.car.CarRepository;

@Configuration
public class CarUseCaseContext {

    @Autowired
    private AppTransaction transaction;

    @Autowired
    private CarRepository repository;

    @Bean
    public CreateCarUseCase createCarUseCase() {
        return new CreateCarUseCase(transaction, repository);
    }

    @Bean
    public GetCarByIdUseCase getCarByIdUseCase() { return new GetCarByIdUseCase(repository); }
}