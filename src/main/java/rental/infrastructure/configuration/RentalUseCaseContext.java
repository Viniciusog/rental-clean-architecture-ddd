package rental.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rental.application.AppTransaction;
import rental.application.rental.CreateRentalUseCase;
import rental.application.rental.DeleteRentalUseCase;
import rental.application.rental.GetRentalByIdUseCase;
import rental.application.rental.GetRentalsByCustomerIdUseCase;
import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarRepository;
import rental.model.customer.CustomerRepository;
import rental.model.rental.RentalPriceCalculator;
import rental.model.rental.RentalRepository;

@Configuration
public class RentalUseCaseContext {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private AppTransaction transaction;

    @Autowired
    private CarAvailabilityChecker carAvailabilityChecker;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    public RentalPriceCalculator rentalPriceCalculator() {
        return new RentalPriceCalculator(carRepository);
    }

    @Bean
    public CreateRentalUseCase createRentalUseCase() {
        return new CreateRentalUseCase(transaction,
                rentalRepository,
                carAvailabilityChecker,
                rentalPriceCalculator(),
                customerRepository);
    }

    @Bean
    public GetRentalByIdUseCase getRentalByIdUseCase() {
        return new GetRentalByIdUseCase(rentalRepository);
    }

    @Bean
    public DeleteRentalUseCase deleteRentalUseCase() {
        return new DeleteRentalUseCase(transaction, rentalRepository);
    }

    @Bean
    public GetRentalsByCustomerIdUseCase getRentalsByCustomerIdUseCase() {
        return new GetRentalsByCustomerIdUseCase(rentalRepository);
    }
}