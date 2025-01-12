package rental.ut.infrastructure.controller.rental.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.rental.dto.CreateRentalRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;

import static org.hamcrest.Matchers.is;
import static rental.fixture.RentalFixture.*;

public class CreateRentalRequestTest {

    @Test
    void mustCreateSucessfully() {
        CreateRentalRequest request = new CreateRentalRequest(
                CUSTOMER_ID.value(),
                CAR_ID.value(),
                RENTAL_START_INSTANT,
                RENTAL_END_INSTANT);

        assertThat(request.customerId(), is(CUSTOMER_ID.value()));
        assertThat(request.carId(), is(CAR_ID.value()));
        assertThat(request.startTime(), is(RENTAL_START_INSTANT));
        assertThat(request.endTime(), is(RENTAL_END_INSTANT));
        assertThat(request.typedCustomerId(), is(CUSTOMER_ID));
        assertThat(request.typedCarId(), is(CAR_ID));
        assertThat(request.typedDateTimeRange(), is(RENTAL_TIME_RANGE));
    }
}