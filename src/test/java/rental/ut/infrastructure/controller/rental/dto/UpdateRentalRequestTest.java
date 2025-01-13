package rental.ut.infrastructure.controller.rental.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.rental.dto.UpdateRentalRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;
import static org.hamcrest.Matchers.is;

public class UpdateRentalRequestTest {

    @Test
    public void createSuccessfully() {
        UpdateRentalRequest request = new UpdateRentalRequest(RENTAL_TIME_RANGE.start(),
                RENTAL_TIME_RANGE.end());

        assertThat(request.startTime(), is(RENTAL_TIME_RANGE.start()));
        assertThat(request.endTime(), is(RENTAL_TIME_RANGE.end()));
    }
}