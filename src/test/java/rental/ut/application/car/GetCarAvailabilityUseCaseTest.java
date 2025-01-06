package rental.ut.application.car;

import liquibase.sqlgenerator.core.CreateIndexGeneratorPostgres;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.application.car.GetCarAvailabilityUseCase;
import rental.model.car.CarAvailabilityChecker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;
import static org.hamcrest.Matchers.is;

public class GetCarAvailabilityUseCaseTest {

    private GetCarAvailabilityUseCase useCase;
    private CarAvailabilityChecker carAvailabilityChecker;

    @BeforeEach
    public void beforeEach() {
        carAvailabilityChecker = mock(CarAvailabilityChecker.class);
        useCase = new GetCarAvailabilityUseCase(carAvailabilityChecker);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void getCarAvailabilityUseCaseSuccessfully(boolean expectedResult) {
        when(carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE))
                .thenReturn(expectedResult);

        boolean result = useCase.execute(CAR_ID, RENTAL_TIME_RANGE);

        assertThat(result, is(expectedResult));
        verify(carAvailabilityChecker).isCarAvailable(CAR_ID, RENTAL_TIME_RANGE);
    }

    @Test
    public void propagateExceptionWhenCarIdIsNull() {
        doThrow(new IllegalArgumentException("carId is required"))
                .when(carAvailabilityChecker)
                .isCarAvailable(null, RENTAL_TIME_RANGE);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(null, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(), is("carId is required"));
    }

    @Test
    public void propagateExceptionWhenTimeRangeIsNull() {
        doThrow(new IllegalArgumentException("timeRange is required"))
                .when(carAvailabilityChecker)
                .isCarAvailable(CAR_ID, null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CAR_ID, null);
        });

        assertThat(exception.getMessage(), is("timeRange is required"));
    }
}
