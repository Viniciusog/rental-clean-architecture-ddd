package rental.application.rental;

import org.apache.commons.collections4.bidimap.AbstractBidiMapDecorator;
import rental.application.AppTransaction;
import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarId;
import rental.model.car.CarRepository;
import rental.model.customer.CustomerId;
import rental.model.exception.CarNotAvailableException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateRentalUseCase {

    private AppTransaction transaction;
    private RentalRepository repository;
    private CarAvailabilityChecker carAvailabilityChecker;

    public CreateRentalUseCase(AppTransaction transaction, RentalRepository repository, CarAvailabilityChecker carAvailabilityChecker) {
        this.transaction = transaction;
        this.repository = repository;
        this.carAvailabilityChecker = carAvailabilityChecker;
    }

    public RentalId execute(CustomerId customerId, CarId carId,
                            LocalDate initialDate, LocalDate endDate,
                            BigDecimal totalPrice) {

        if (!carAvailabilityChecker.isCarAvailable(carId, initialDate, endDate)) {
            throw new CarNotAvailableException(carId);
        }
        Rental rental = Rental.builder()
                .carId(carId)
                .customerId(customerId)
                .initialDate(initialDate)
                .endDate(endDate)
                .totalPrice(totalPrice)
                .build();

        transaction.execute(() -> {
            repository.save(rental);
        });
        return rental.id();
    }
}