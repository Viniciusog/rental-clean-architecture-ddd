package rental.ut.application.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.AppTransaction;
import rental.application.car.CreateCarUseCase;
import rental.fixture.CarFixture;
import rental.model.car.Car;
import rental.model.car.CarRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.AppTransactionFixture.assertThatInTransaction;
import static rental.fixture.AppTransactionFixture.mockedTransaction;

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
        Car car = CarFixture.aCarWithoutId().build();

        useCase.execute(CarFixture.MODEL, CarFixture.YEAR);

        verify(repository).save(car);
    }
}