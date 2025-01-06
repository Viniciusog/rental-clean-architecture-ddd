package rental.ut.model.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.car.CarAvailabilityChecker;
import rental.model.exception.CarNotAvailableException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.RentalFixture.*;
import static org.hamcrest.Matchers.is;

public class CarAvailabilityCheckerTest {

    private RentalRepository rentalRepository;
    private CarAvailabilityChecker carAvailabilityChecker;

    @BeforeEach
    public void beforeEach() {
        rentalRepository = mock(RentalRepository.class);
        carAvailabilityChecker = new CarAvailabilityChecker(rentalRepository);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void mustReturnTrueAvailabilitySuccessfully(boolean expectedResult) {
        when(rentalRepository.existsByCarIdAndTimeRange(CAR_ID, RENTAL_TIME_RANGE))
                .thenReturn(expectedResult);

        boolean available = carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE);

        assertThat(available, is(expectedResult));
    }

    @Test
    void throwsExceptionWhenCarIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            carAvailabilityChecker.isCarAvailable(null, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(), is("carId is required"));
    }

    @Test
    void throwsExceptionWhenTimeRangeIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            carAvailabilityChecker.isCarAvailable(CAR_ID, null);
        });

        assertThat(exception.getMessage(), is("timeRange is required"));
    }

    @Test
    void mustThrowExceptionWhenCarIsNotAvailable() {
        when(rentalRepository.existsByCarIdAndTimeRange(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(false);

        CarNotAvailableException exception = assertThrows(CarNotAvailableException.class, () -> {
            carAvailabilityChecker.carIsAvailableOrThrowException(CAR_ID, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(),
                is(String.format("Car with id: %s is not available for the given time range: %s to %s",
                CAR_ID.value(), RENTAL_TIME_RANGE.start(), RENTAL_TIME_RANGE.end()))
        );
    }

    @Test
    void doesNotThrowExceptionWhenCarIsAvailable() {
        when(rentalRepository.existsByCarIdAndTimeRange(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(true);

        assertDoesNotThrow(() -> {
            carAvailabilityChecker.carIsAvailableOrThrowException(CAR_ID, RENTAL_TIME_RANGE);
        });
    }
}