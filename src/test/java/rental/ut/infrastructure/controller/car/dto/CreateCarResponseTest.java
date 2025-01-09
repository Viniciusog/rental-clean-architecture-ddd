package rental.ut.infrastructure.controller.car.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.car.dto.CreateCarResponse;
import rental.model.car.CarId;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.CarFixture.CAR_ID;

public class CreateCarResponseTest {

    @Test
    void createSucessfully() {
        CarId carId = CAR_ID;

        CreateCarResponse response = CreateCarResponse.of(carId);

        assertThat(response.id(), is(carId.value()));
    }
}
