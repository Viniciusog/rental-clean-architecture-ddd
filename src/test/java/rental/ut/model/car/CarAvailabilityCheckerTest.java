package rental.ut.model.car;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.fixture.RentalFixture;
import rental.model.car.CarAvailabilityChecker;
import rental.model.rental.RentalRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
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
}
