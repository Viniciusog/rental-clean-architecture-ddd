package rental.ut.infrastructure.controller.rental.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.rental.dto.RentalResponse;
import rental.model.rental.Rental;

import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.RentalFixture.aRentalWithId;
import static org.hamcrest.Matchers.is;

public class RentalResponseTest {

    @Test
    void createSuccessfullyFromRental() {
        Rental rental = aRentalWithId().build();

        RentalResponse response = RentalResponse.of(rental);

        assertThat(rental.id().value(), is(response.id()));
        assertThat(rental.customerId().value(), is(response.customerId()));
        assertThat(rental.carId().value(), is(response.carId()));
        assertThat(rental.timeRange().start(), is(response.startTime()));
        assertThat(rental.timeRange().end(), is(response.endTime()));
        assertThat(rental.totalPrice(), is(response.totalPrice()));
    }
}
