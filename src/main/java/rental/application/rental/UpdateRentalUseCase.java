package rental.application.rental;

import rental.application.AppTransaction;
import rental.model.car.CarAvailabilityChecker;
import rental.model.exception.CarNotAvailableException;
import rental.model.exception.RentalNotFoundException;
import rental.model.rental.*;

import java.math.BigDecimal;

public class UpdateRentalUseCase {

    private AppTransaction transaction;
    private RentalRepository rentalRepository;
    private CarAvailabilityChecker carAvailabilityChecker;
    private RentalPriceCalculator rentalPriceCalculator;

    public UpdateRentalUseCase(AppTransaction transaction,
                               RentalRepository rentalRepository,
                               CarAvailabilityChecker carAvailabilityChecker,
                               RentalPriceCalculator rentalPriceCalculator) {
        this.transaction = transaction;
        this.rentalRepository = rentalRepository;
        this.carAvailabilityChecker = carAvailabilityChecker;
        this.rentalPriceCalculator = rentalPriceCalculator;
    }

    public void execute(RentalId rentalId, DateTimeRange newTimeRange) {
        transaction.execute(() -> {
            Rental rental = rentalRepository.getById(rentalId)
                    .orElseThrow(() -> new RentalNotFoundException(rentalId));

            if (!carAvailabilityChecker.isCarAvailable(rental.carId(), newTimeRange)) {
                throw new CarNotAvailableException(rental.carId(), newTimeRange);
            }

            BigDecimal newRentalTotalPrice = rentalPriceCalculator.execute(rental.carId(), newTimeRange);

            rental.update(newTimeRange, newRentalTotalPrice);

            rentalRepository.save(rental);
        });
    }
}