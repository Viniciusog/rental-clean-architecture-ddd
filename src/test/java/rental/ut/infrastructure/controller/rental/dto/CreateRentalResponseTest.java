package rental.ut.infrastructure.controller.rental.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.rental.dto.CreateRentalResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.RentalFixture.RENTAL_ID;

import static org.hamcrest.Matchers.is;

public class CreateRentalResponseTest {

    @Test
    void mustCreateSuccessfully() {

        CreateRentalResponse response = new CreateRentalResponse(RENTAL_ID.value());

        assertThat(response.id(), is(RENTAL_ID.value()));
    }
}
