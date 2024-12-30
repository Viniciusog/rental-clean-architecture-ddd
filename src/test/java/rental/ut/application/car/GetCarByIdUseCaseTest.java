package rental.ut.application.car;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.AppTransaction;
import rental.application.car.GetCarByIdUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.car.Car;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static rental.fixture.CarFixture.aCarWithId;

public class GetCarByIdUseCaseTest {

    private CarRepository repository;
    private GetCarByIdUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        repository = mock(CarRepository.class);
        useCase = new GetCarByIdUseCase(repository);
    }

    @Test
    void getCarById() {
        Car car = aCarWithId().build();
        when(repository.getById(CAR_ID)).thenReturn(Optional.of(car));

        Car retrieved = useCase.execute(CAR_ID);

        verify(repository).getById(CAR_ID);
        assertThat(car, is(retrieved));
    }

    @Test
    void throwsExceptionWhenCarWasNotFound() {
        when(repository.getById(CAR_ID))
                .thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class,
                () -> useCase.execute(CAR_ID));
        verify(repository).getById(CAR_ID);
    }
}