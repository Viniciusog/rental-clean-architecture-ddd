package rental.ut.application.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.AppTransaction;
import rental.application.rental.CreateRentalUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.car.CarAvailabilityChecker;
import rental.model.exception.CarNotAvailableException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalPriceCalculator;
import rental.model.rental.RentalRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;
import static rental.fixture.RentalFixture.*;
import static org.hamcrest.Matchers.is;

public class CreateRentalUseCaseTest {

    private AppTransaction transaction;
    private RentalRepository repository;
    private CreateRentalUseCase useCase;
    private CarAvailabilityChecker carAvailabilityChecker;
    private RentalPriceCalculator rentalPriceCalculator;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(RentalRepository.class);
        carAvailabilityChecker = mock(CarAvailabilityChecker.class);
        rentalPriceCalculator = mock(RentalPriceCalculator.class);
        useCase = new CreateRentalUseCase(transaction, repository,
                carAvailabilityChecker, rentalPriceCalculator);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).save(any());
    }

    @Test
    public void mustCreateSuccessfully() {
        when(carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE))
                .thenReturn(true);
        when(rentalPriceCalculator.execute(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(RENTAL_TOTAL_PRICE);
        Rental expectedRental = Rental.builder()
                .customerId(CUSTOMER_ID)
                .carId(CAR_ID)
                .timeRange(RENTAL_TIME_RANGE)
                .totalPrice(RENTAL_TOTAL_PRICE)
                .build();

        RentalId id = useCase.execute(CUSTOMER_ID, CAR_ID, RENTAL_TIME_RANGE);

        verify(repository).save(expectedRental);
    }

    @Test
    public void throwsExceptionWhenCarIsNotAvailable() {
        when(carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE))
                .thenReturn(false);
        when(rentalPriceCalculator.execute(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(RENTAL_TOTAL_PRICE);

        CarNotAvailableException exception = assertThrows(CarNotAvailableException.class, () -> {
           useCase.execute(
                   CUSTOMER_ID,
                   CAR_ID,
                   RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(),
                is(String.format("Car with id: %s is not available for the given time range: %s to %s",
                        CAR_ID.value(), RENTAL_TIME_RANGE.start(), RENTAL_TIME_RANGE.end())));
        verify(repository, never()).save(any());
    }
}