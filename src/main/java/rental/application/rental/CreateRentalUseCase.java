package rental.application.rental;

import rental.application.AppTransaction;
import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CarNotAvailableException;
import rental.model.exception.CustomerNotFoundException;
import rental.model.rental.*;

import java.math.BigDecimal;

public class CreateRentalUseCase {

    private AppTransaction transaction;
    private RentalRepository repository;
    private CarAvailabilityChecker carAvailabilityChecker;
    private RentalPriceCalculator rentalPriceCalculator;
    private CustomerRepository customerRepository;

    public CreateRentalUseCase(AppTransaction transaction,
                               RentalRepository repository,
                               CarAvailabilityChecker carAvailabilityChecker,
                               RentalPriceCalculator rentalPriceCalculator,
                               CustomerRepository customerRepository) {
        this.transaction = transaction;
        this.repository = repository;
        this.carAvailabilityChecker = carAvailabilityChecker;
        this.rentalPriceCalculator = rentalPriceCalculator;
        this.customerRepository = customerRepository;
    }

    public RentalId execute(CustomerId customerId,
                            CarId carId,
                            DateTimeRange timeRange) {
        customerRepository.getById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        carAvailabilityChecker.ensureCarIsAvailableOrThrowException(carId, timeRange);

        BigDecimal calculatedRentalPrice = rentalPriceCalculator.execute(carId, timeRange);

        Rental rental = Rental.builder()
                .carId(carId)
                .customerId(customerId)
                .timeRange(timeRange)
                .totalPrice(calculatedRentalPrice)
                .build();

        transaction.execute(() -> {
            repository.save(rental);
        });
        return rental.id();
    }
}