package rental.ut.model.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.car.Car;
import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotAvailableException;
import rental.model.exception.CarNotFoundException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
    private CarRepository carRepository;
    private CarAvailabilityChecker carAvailabilityChecker;

    @BeforeEach
    public void beforeEach() {
        rentalRepository = mock(RentalRepository.class);
        carRepository = mock(CarRepository.class);
        carAvailabilityChecker = new CarAvailabilityChecker(rentalRepository, carRepository);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void mustReturnTrueAvailabilitySuccessfully(boolean existsInDatabase) {
        Car car = mock(Car.class);
        when(rentalRepository.existsByCarIdAndTimeRange(CAR_ID, RENTAL_TIME_RANGE))
                .thenReturn(existsInDatabase);
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.of(car));

        boolean available = carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE);

        assertThat(available, is(!existsInDatabase));
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
        Car car = mock(Car.class);
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.of(car));
        when(rentalRepository.existsByCarIdAndTimeRange(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(true);

        CarNotAvailableException exception = assertThrows(CarNotAvailableException.class, () -> {
            carAvailabilityChecker.ensureCarIsAvailableOrThrowException(CAR_ID, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(),
                is(String.format("Car with id: %s is not available for the given time range: %s to %s",
                CAR_ID.value(), RENTAL_TIME_RANGE.start(), RENTAL_TIME_RANGE.end()))
        );
    }

    @Test
    void doesNotThrowExceptionWhenCarIsAvailable() {
        Car car = mock(Car.class);
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.of(car));
        when(rentalRepository.existsByCarIdAndTimeRange(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(false);

        assertDoesNotThrow(() -> {
            carAvailabilityChecker.ensureCarIsAvailableOrThrowException(CAR_ID, RENTAL_TIME_RANGE);
        });
    }

    @Test
    void isCarAvailableThrowsExceptionWhenCarWasNotFound() {
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> {
            carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE);
        });
    }

    @Test
    void propagateExceptionWhenCarWasNotFound() {
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> {
            carAvailabilityChecker.ensureCarIsAvailableOrThrowException(CAR_ID, RENTAL_TIME_RANGE);
        });
    }

    @Test
    void ensureCarIsAvailableWithRentalExclusionThrowsException() {
        Car car = mock(Car.class);
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.of(car));
        when(rentalRepository.existsByCarIdAndTimeRangeWithRentalExclusion(
                CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID))
                .thenReturn(true);

        assertThrows(CarNotAvailableException.class, () -> {
            carAvailabilityChecker.ensureCarIsAvailableWithRentalExclusionOrThrowException(
                    CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID);
        });
    }

    @Test
    void ensureCarIsAvailableWithRentalExclusionDoesNotThrow() {
        Car car = mock(Car.class);
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.of(car));
        when(rentalRepository.existsByCarIdAndTimeRangeWithRentalExclusion(
                CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID))
                .thenReturn(false);

        assertDoesNotThrow(() -> {
            carAvailabilityChecker.ensureCarIsAvailableWithRentalExclusionOrThrowException(
                    CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID);
        });
    }

    @Test
    void ensureCarIsAvailableWithRentalExclusionThrowsCarNotFound() {
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.empty());
        when(rentalRepository.existsByCarIdAndTimeRangeWithRentalExclusion(
                CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID))
                .thenReturn(false);

        assertThrows(CarNotFoundException.class, () -> {
            carAvailabilityChecker.ensureCarIsAvailableWithRentalExclusionOrThrowException(
                    CAR_ID, RENTAL_TIME_RANGE, RENTAL_ID);
        });
    }

    @Test
    void ensureCarIsAvailableWithRentalExclusionThrowsCarIdIsRequired() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            carAvailabilityChecker.ensureCarIsAvailableWithRentalExclusionOrThrowException(
                    null, RENTAL_TIME_RANGE, RENTAL_ID);
        });

        assertThat(exception.getMessage(), is("carId is required"));
    }

    @Test
    void ensureCarIsAvailableWithRentalExclusionThrowsTimeRangeIsRequired() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            carAvailabilityChecker.ensureCarIsAvailableWithRentalExclusionOrThrowException(
                    CAR_ID, null, RENTAL_ID);
        });

        assertThat(exception.getMessage(), is("timeRange is required"));
    }

    @Test
    void ensureCarIsAvailableWithRentalExclusionThrowsRentalIdIsRequired() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            carAvailabilityChecker.ensureCarIsAvailableWithRentalExclusionOrThrowException(
                    CAR_ID, RENTAL_TIME_RANGE, null);
        });

        assertThat(exception.getMessage(), is("rentalId is required"));
    }
}