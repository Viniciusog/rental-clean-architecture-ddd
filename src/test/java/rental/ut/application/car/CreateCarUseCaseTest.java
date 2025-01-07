package rental.ut.application.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import rental.application.AppTransaction;
import rental.application.car.CreateCarUseCase;
import rental.fixture.CarFixture;
import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.CarRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.AppTransactionFixture.assertThatInTransaction;
import static rental.fixture.AppTransactionFixture.mockedTransaction;
import static  org.hamcrest.Matchers.is;
import static rental.fixture.CarFixture.*;

public class CreateCarUseCaseTest {

    private AppTransaction transaction;
    private CarRepository repository;
    private CreateCarUseCase useCase;

    @BeforeEach
    void beforeEach() {
        transaction = mockedTransaction();
        repository = mock(CarRepository.class);
        useCase = new CreateCarUseCase(transaction, repository);
        assertThatInTransaction(transaction)
                .when(repository).save(any());
    }

    @Test
    void mustCreateACar() {
        Car expectedCarAfterRepositoryExecution = CarFixture.aCarWithoutId().id(CAR_ID).build();
        doAnswer(invocation -> {
            Car carArgument = invocation.getArgument(0);
            carArgument.created(CAR_ID);
            return null;
        }).when(repository).save(any(Car.class));

        CarId idReturned = useCase.execute(CarFixture.MODEL, CarFixture.YEAR, DAILY_PRICE);

        verify(repository).save(expectedCarAfterRepositoryExecution);
        assertThat(idReturned, is(CAR_ID));
    }
}