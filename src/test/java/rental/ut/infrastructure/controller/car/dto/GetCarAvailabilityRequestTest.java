package rental.ut.infrastructure.controller.car.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.car.dto.GetCarAvailabilityRequest;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;
import static org.hamcrest.Matchers.is;

public class GetCarAvailabilityRequestTest {

    @Test
    public void createSuccessfully() {
        Instant start = RENTAL_TIME_RANGE.start();
        Instant end = RENTAL_TIME_RANGE.end();
        GetCarAvailabilityRequest request = new GetCarAvailabilityRequest(start, end);

        assertThat(request.startTime(), is(RENTAL_TIME_RANGE.start()));
        assertThat(request.endTime(), is(RENTAL_TIME_RANGE.end()));
    }
}