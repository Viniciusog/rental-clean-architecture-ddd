package rental.ut.infrastructure.repository.car;

import org.junit.jupiter.api.Test;
import rental.fixture.CarFixture;
import rental.infrastructure.repository.car.CarEntity;
import rental.model.car.Car;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CarEntityTest {

    @Test
    void mustCreateCarEntityFromCar() {
        Car car = CarFixture.aCarWithId().build();

        CarEntity entity = new CarEntity(car);
        Car carFromEntity = entity.toCar();

        assertThat(car, is(carFromEntity));
        assertThat(car.id().value(), is(entity.id()));
    }
}
