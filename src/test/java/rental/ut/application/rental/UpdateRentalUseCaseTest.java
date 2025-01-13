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
import rental.model.exception.CarNotFoundException;
import rental.model.exception.RentalNotFoundException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalPriceCalculator;
import rental.model.rental.RentalRepository;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.RentalFixture.*;

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
        doNothing().when(carAvailabilityChecker)
                .ensureCarIsAvailableWithRentalExclusionOrThrowException(CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID);
        when(rentalPriceCalculator.execute(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(RENTAL_TOTAL_PRICE);

        useCase.execute(RENTAL_ID, RENTAL_TIME_RANGE);

        InOrder inOrder = inOrder(rental, rentalRepository);
        inOrder.verify(rental).update(RENTAL_TIME_RANGE, RENTAL_TOTAL_PRICE);
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
        doThrow(new CarNotAvailableException(CAR_ID, RENTAL_TIME_RANGE))
                .when(carAvailabilityChecker)
                .ensureCarIsAvailableWithRentalExclusionOrThrowException(CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID);

        CarNotAvailableException exception = assertThrows(CarNotAvailableException.class, () -> {
           useCase.execute(RENTAL_ID, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(), is(String.format("Car with id: %s is not available for the given time range: %s to %s",
                CAR_ID.value(), RENTAL_TIME_RANGE.start(), RENTAL_TIME_RANGE.end())));
        verify(rentalRepository, never()).save(any());
    }

    @Test
    void propagateExceptionWhenCarWasNotFound() {
        Rental rental = mock(Rental.class);
        when(rental.carId()).thenReturn(CAR_ID);
        when(rentalRepository.getById(RENTAL_ID)).thenReturn(Optional.of(rental));
        doThrow(new CarNotFoundException(CAR_ID)).when(carAvailabilityChecker)
                .ensureCarIsAvailableWithRentalExclusionOrThrowException(CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID);

        assertThrows(CarNotFoundException.class, () -> {
           useCase.execute(RENTAL_ID, RENTAL_TIME_RANGE);
        });

        verify(rentalRepository, never()).save(any());
    }

    @Test
    void propagateExceptionWhenTimeRangeIsNull() {
        Rental rental = mock(Rental.class);
        when(rental.carId()).thenReturn(CAR_ID);
        when(rentalRepository.getById(RENTAL_ID)).thenReturn(Optional.of(rental));
        doThrow(new IllegalArgumentException("timeRange is required"))
                .when(carAvailabilityChecker)
                .ensureCarIsAvailableWithRentalExclusionOrThrowException(CAR_ID, null, RENTAL_ID);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(RENTAL_ID, null);
        });

        assertThat(exception.getMessage(), is("timeRange is required"));
        verify(rentalRepository, never()).save(any());
    }
}