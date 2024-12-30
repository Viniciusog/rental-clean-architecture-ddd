package rental.ut.application.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import rental.application.AppTransaction;
import rental.application.car.UpdateCarUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.car.Car;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateCarUseCaseTest {

    private AppTransaction transaction;
    private CarRepository repository;
    private UpdateCarUseCase useCase;

    @BeforeEach
    void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(CarRepository.class);
        useCase = new UpdateCarUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction)
                .when(repository).save(any());
    }

    @Test
    void mustUpdateSuccessfully() {
        Car car = mock(Car.class);
        when(repository.getById(CAR_ID)).thenReturn(Optional.of(car));

        useCase.execute(CAR_ID, MODEL, YEAR);

        InOrder inOrder = Mockito.inOrder(car, repository);
        inOrder.verify(car).update(MODEL, YEAR);
        inOrder.verify(repository).save(car);
    }

    @Test
    void throwsExceptionWhenCarDoesNotExists() {
        when(repository.getById(CAR_ID))
                .thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class,
                () -> useCase.execute(CAR_ID, MODEL, YEAR));

        verify(repository, never()).save(any());
    }

    @Test
    void throwsExceptionWhenCarModelIsNull() {
        when(repository.getById(CAR_ID))
                .thenReturn(Optional.of(aCarWithId().build()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(CAR_ID, null, YEAR));

        assertThat(exception.getMessage(), is("Car model is required."));
        verify(repository, never()).save(any());
    }

    @Test
    void throwsExceptionWhenCarYearIsNull() {
        when(repository.getById(CAR_ID))
                .thenReturn(Optional.of(aCarWithId().build()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(CAR_ID, MODEL, null));

        assertThat(exception.getMessage(), is("Car year is required."));
        verify(repository, never()).save(any());
    }
}