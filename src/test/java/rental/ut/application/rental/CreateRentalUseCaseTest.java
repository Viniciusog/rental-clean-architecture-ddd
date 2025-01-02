package rental.ut.application.rental;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import rental.application.AppTransaction;
import rental.application.rental.CreateRentalUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.car.CarId;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;
import static rental.fixture.RentalFixture.*;
import static rental.ut.model.Rental.RentalTest.*;


public class CreateRentalUseCaseTest {

    private AppTransaction transaction;
    private RentalRepository repository;
    private CreateRentalUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(RentalRepository.class);
        useCase = new CreateRentalUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).save(any());
    }

    @Test
    public void mustCreateSuccessfully() {
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
}