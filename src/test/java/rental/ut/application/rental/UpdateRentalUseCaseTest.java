package rental.ut.application.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import rental.application.AppTransaction;
import rental.application.rental.UpdateRentalUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotAvailableException;
import rental.model.exception.RentalNotFoundException;
import rental.model.rental.DateTimeRange;
import rental.model.rental.Rental;
import rental.model.rental.RentalPriceCalculator;
import rental.model.rental.RentalRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.RentalFixture.RENTAL_ID;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;

public class UpdateRentalUseCaseTest {

    private AppTransaction transaction;
    private RentalRepository rentalRepository;
    private CarAvailabilityChecker carAvailabilityChecker;
    private RentalPriceCalculator rentalPriceCalculator;
    private UpdateRentalUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        rentalRepository = mock(RentalRepository.class);
        carAvailabilityChecker = mock(CarAvailabilityChecker.class);
        rentalPriceCalculator = mock(RentalPriceCalculator.class);
        useCase = new UpdateRentalUseCase(transaction, rentalRepository,
                carAvailabilityChecker, rentalPriceCalculator);
        AppTransactionFixture.assertThatInTransaction(transaction).when(rentalRepository).save(any());
    }

    @Test
    void mustUpdateRental() {
        Rental rental = mock(Rental.class);
        when(rental.carId()).thenReturn(CAR_ID);
        when(rentalRepository.getById(RENTAL_ID)).thenReturn(Optional.of(rental));
        DateTimeRange newTimeRange = DateTimeRange.of(
                LocalDateTime.of(2025, 2, 10, 6, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 20, 6, 0, 0).toInstant(ZoneOffset.UTC)
        );
        BigDecimal newTotalPrice = BigDecimal.valueOf(1000.00);
        when(carAvailabilityChecker.isCarAvailable(CAR_ID, newTimeRange)).thenReturn(true);
        when(rentalPriceCalculator.execute(CAR_ID, newTimeRange)).thenReturn(newTotalPrice);

        useCase.execute(RENTAL_ID, newTimeRange);

        InOrder inOrder = inOrder(rental, rentalRepository);
        inOrder.verify(rental).update(newTimeRange, newTotalPrice);
        inOrder.verify(rentalRepository).save(rental);
    }

    @Test
    void throwsExceptionWhenRentalWasNotFound() {
        when(rentalRepository.getById(RENTAL_ID)).thenReturn(Optional.empty());

        RentalNotFoundException exception = assertThrows(RentalNotFoundException.class, () -> {
            useCase.execute(RENTAL_ID, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(), is("Rental not found with id: " + RENTAL_ID.value()));
        verify(rentalRepository, never()).save(any());
    }

    @Test
    void throwsExceptionWhenCarIsNotAvailable() {
        Rental rental = mock(Rental.class);
        when(rental.carId()).thenReturn(CAR_ID);
        when(rentalRepository.getById(RENTAL_ID)).thenReturn(Optional.of(rental));
        when(carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(false);

        CarNotAvailableException exception = assertThrows(CarNotAvailableException.class, () -> {
           useCase.execute(RENTAL_ID, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(), is("Car with id: " + CAR_ID.value() + " is not available to rent."));
        verify(rentalRepository, never()).save(any());
    }

    @Test
    void propagateExceptionWhenTimeRangeIsNull() {
        Rental rental = mock(Rental.class);
        when(rental.carId()).thenReturn(CAR_ID);
        when(rentalRepository.getById(RENTAL_ID)).thenReturn(Optional.of(rental));
        doThrow(IllegalArgumentException.class).when(carAvailabilityChecker).isCarAvailable(CAR_ID, null);

        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(RENTAL_ID, null);
        });

        verify(rentalRepository, never()).save(any());
    }
}