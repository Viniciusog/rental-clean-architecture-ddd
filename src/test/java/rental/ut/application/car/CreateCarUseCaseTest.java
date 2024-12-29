package rental.ut.application.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.AppTransaction;
import rental.application.car.CreateCarUseCase;
import rental.fixture.CarFixture;
import rental.model.car.Car;
import rental.model.car.CarRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class CreateCarUseCaseTest {

    private AppTransaction transaction;
    private CarRepository repository;
    private CreateCarUseCase useCase;

    @BeforeEach
    void beforeEach() {
        transaction = mock(AppTransaction.class);
        repository = mock(CarRepository.class);
        useCase = new CreateCarUseCase(transaction, repository);
        mockTransactionExecute();
        mockRepositorySave();
    }

    private void mockTransactionExecute() {
        doAnswer(invocationOnMock -> {
            when(transaction.inTransaction()).thenReturn(true);
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            when(transaction.inTransaction()).thenReturn(false);
            return null;
        }).when(transaction).execute(any());
    }

    private void mockRepositorySave() {
        doAnswer(invocationOnMock -> {
            assertThat(transaction.inTransaction(), is(true));
            return null;
        }).when(repository).save(any());
    }

    @Test
    void mustCreateACar() {
        Car car = CarFixture.aCarWithoutId().build();

        useCase.execute(CarFixture.MODEL, CarFixture.YEAR);

        verify(repository).save(car);
    }
}
