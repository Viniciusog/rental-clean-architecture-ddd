package rental.ut.infrastructure.controller.car.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.car.dto.CarResponse;
import rental.model.car.Car;

import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.CarFixture.aCarWithId;
import static org.hamcrest.Matchers.is;

public class CarResponseTest {

    @Test
    void mustCreateSuccessfullyFromCar() {
        Car car = aCarWithId().build();

        CarResponse response = CarResponse.of(car);

        assertThat(response.id(), is(car.id().value()));
        assertThat(response.dailyPrice(), is(car.dailyPrice()));
        assertThat(response.make(), is(car.model().make().value()));
        assertThat(response.model(), is(car.model().name()));
        assertThat(response.year(), is((short)car.year().getValue()));
    }
}