package rental.ut.application.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.AppTransaction;
import rental.application.rental.CreateRentalUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.car.CarAvailabilityChecker;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CarNotAvailableException;
import rental.model.exception.CarNotFoundException;
import rental.model.exception.CustomerNotFoundException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalPriceCalculator;
import rental.model.rental.RentalRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;
import static rental.fixture.RentalFixture.*;

public class CreateRentalUseCaseTest {

    private AppTransaction transaction;
    private RentalRepository repository;
    private CreateRentalUseCase useCase;
    private CarAvailabilityChecker carAvailabilityChecker;
    private RentalPriceCalculator rentalPriceCalculator;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(RentalRepository.class);
        carAvailabilityChecker = mock(CarAvailabilityChecker.class);
        rentalPriceCalculator = mock(RentalPriceCalculator.class);
        customerRepository = mock(CustomerRepository.class);
        useCase = new CreateRentalUseCase(transaction, repository,
                carAvailabilityChecker, rentalPriceCalculator, customerRepository);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).save(any());
    }

    @Test
    public void mustCreateSuccessfully() {
        doNothing()
                .when(carAvailabilityChecker)
                .ensureCarIsAvailableOrThrowException(CAR_ID, RENTAL_TIME_RANGE);
        when(rentalPriceCalculator.execute(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(RENTAL_TOTAL_PRICE);
        Rental expectedRentalAfterRepositoryExecution = Rental.builder()
                .id(RENTAL_ID)
                .customerId(CUSTOMER_ID)
                .carId(CAR_ID)
                .timeRange(RENTAL_TIME_RANGE)
                .totalPrice(RENTAL_TOTAL_PRICE)
                .build();
        doAnswer(invocation -> {
            Rental rental = invocation.getArgument(0);
            rental.created(RENTAL_ID);
            return null;
        }).when(repository).save(any(Rental.class));

        RentalId id = useCase.execute(CUSTOMER_ID, CAR_ID, RENTAL_TIME_RANGE);

        verify(repository).save(expectedRentalAfterRepositoryExecution);
        assertThat(id, is(RENTAL_ID));
    }

    @Test
    public void throwsExceptionWhenCarIsNotAvailable() {
        doThrow(new CarNotAvailableException(CAR_ID, RENTAL_TIME_RANGE))
                .when(carAvailabilityChecker)
                .ensureCarIsAvailableOrThrowException(CAR_ID, RENTAL_TIME_RANGE);
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

    @Test
    void propagateExceptionWhenCarWasNotFound() {
        doThrow(new CarNotFoundException(CAR_ID))
                .when(rentalPriceCalculator).execute(CAR_ID, RENTAL_TIME_RANGE);

        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> {
           useCase.execute(CUSTOMER_ID, CAR_ID, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(), is("Car not found with id: " + CAR_ID.value()));
    }

    @Test
    void throwsExceptionWhenCustomerWasNotFound() {
        when(customerRepository.getById(CUSTOMER_ID)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            useCase.execute(CUSTOMER_ID, CAR_ID, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(), is("Customer not found with id: " + CUSTOMER_ID.value()));
    }
}