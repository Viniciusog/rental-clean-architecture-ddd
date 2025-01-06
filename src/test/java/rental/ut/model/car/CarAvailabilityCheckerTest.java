package rental.ut.model.car;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.fixture.RentalFixture;
import rental.model.car.CarAvailabilityChecker;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.exception.CarNotAvailableException;
import rental.model.rental.DateTimeRange;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
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

    private RentalRepository repository;
    private CarAvailabilityChecker carAvailabilityChecker;

    @BeforeEach
    public void beforeEach() {
        repository = mock(RentalRepository.class);
        carAvailabilityChecker = new CarAvailabilityChecker(repository);
    }

    @Test
    void mustReturnTrueToCarAvailability() {
        when(repository.getByCarIdAndDateInterval(CAR_ID, RENTAL_TIME_RANGE))
                .thenReturn(List.of());

        boolean available = carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE);

        assertThat(available, is(true));
    }

    @Test
    void mustReturnFalseToCarAvailability() {
        when(repository.getByCarIdAndDateInterval(CAR_ID, RENTAL_TIME_RANGE))
                .thenReturn(List.of(aRentalWithId().build()));

        boolean available = carAvailabilityChecker.isCarAvailable(CAR_ID, RENTAL_TIME_RANGE);

        assertThat(available, is(false));
    }

    @Test
    void throwsExceptionWhenCarIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            carAvailabilityChecker.isCarAvailable(null, RENTAL_TIME_RANGE);
        });

        assertThat(exception.getMessage(), is("carId is required"));
    }

    @Test
    void throwsExceptionWhemTimeRangeIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            carAvailabilityChecker.isCarAvailable(CAR_ID, null);
        });

        assertThat(exception.getMessage(), is("timeRange is required"));
    }

    @Test
    void mustThrowExceptionWhenCarIsNotAvailable() {
        List<Rental> expectedRentals = List.of(
                aRentalWithId().id(RentalId.of(1L)).build(),
                aRentalWithId().id(RentalId.of(2L)).build()
        );
        when(repository.getByCarIdAndDateInterval(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(expectedRentals);

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
        List<Rental> expectedRentals = List.of();
        when(repository.getByCarIdAndDateInterval(CAR_ID, RENTAL_TIME_RANGE)).thenReturn(expectedRentals);

        assertDoesNotThrow(() -> {
            carAvailabilityChecker.carIsAvailableOrThrowException(CAR_ID, RENTAL_TIME_RANGE);
        });
    }
}