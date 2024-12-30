package rental.ut.application.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.AppTransaction;
import rental.application.car.DeleteCarUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.car.Car;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCarUseCaseTest {

    private AppTransaction transaction;
    private CarRepository repository;
    private DeleteCarUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(CarRepository.class);
        useCase = new DeleteCarUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction)
                .when(repository).deleteById(any());
    }

    @Test
    void mustDeleteCar() {
        Car car = mock(Car.class);
        when(repository.getById(CAR_ID)).thenReturn(Optional.of(car));

        useCase.execute(CAR_ID);

        verify(repository).deleteById(CAR_ID);
    }

    @Test
    void mustPropagateRepositoryExceptionWhenCarWasNotFound() {
        doThrow(CarNotFoundException.class)
                .when(repository).deleteById(CAR_ID);

        assertThrows(CarNotFoundException.class,
                () -> useCase.execute(CAR_ID));
    }
}
