package rental.application.rental;

import org.apache.commons.collections4.bidimap.AbstractBidiMapDecorator;
import rental.application.AppTransaction;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateRentalUseCase {

    private AppTransaction transaction;
    private RentalRepository repository;

    public CreateRentalUseCase(AppTransaction transaction, RentalRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public RentalId execute(CustomerId customerId, CarId carId, LocalDate initialDate, LocalDate endDate, BigDecimal totalPrice) {
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