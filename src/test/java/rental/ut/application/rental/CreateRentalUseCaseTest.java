package rental.ut.application.rental;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import rental.application.AppTransaction;
import rental.application.rental.CreateRentalUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarId;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.exception.CarNotAvailableException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
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

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(RentalRepository.class);
        carAvailabilityChecker = mock(CarAvailabilityChecker.class);
        useCase = new CreateRentalUseCase(transaction, repository, carAvailabilityChecker);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).save(any());
    }

    @Test
    public void mustCreateSuccessfully() {
        when(carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_INITIAL_DATE, RENTAL_END_DATE))
                .thenReturn(true);
        Rental expectedRental = Rental.builder()
                .customerId(CUSTOMER_ID)
                .carId(CAR_ID)
                .initialDate(RENTAL_INITIAL_DATE)
                .endDate(RENTAL_END_DATE)
                .totalPrice(RENTAL_TOTAL_PRICE)
                .build();

        RentalId id = useCase.execute(CUSTOMER_ID,
                CAR_ID,
                RENTAL_INITIAL_DATE,
                RENTAL_END_DATE,
                RENTAL_TOTAL_PRICE);

        verify(repository).save(expectedRental);
    }

    @Test
    public void throwsExceptionWhenCarIsNotAvailable() {
        when(carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_INITIAL_DATE, RENTAL_END_DATE))
                .thenReturn(false);

        CarNotAvailableException exception = assertThrows(CarNotAvailableException.class, () -> {
           useCase.execute(
                   CUSTOMER_ID,
                   CAR_ID,
                   RENTAL_INITIAL_DATE,
                   RENTAL_END_DATE,
                   RENTAL_TOTAL_PRICE);
        });

        assertThat(exception.getMessage(),
                is("Car with id: " + CAR_ID.value() + " is not available to rent."));    }
}