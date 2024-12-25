package rental.ut.model.car;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import rental.model.car.Car;
import rental.model.car.Make;
import rental.model.car.Model;

import java.time.Year;

import static rental.fixture.CarFixture.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rental.fixture.CarFixture.*;

public class CarTest {

    @Test
    public void createSuccessfully() {
        Car car = Car.builder().model(MODEL).year(YEAR).build();

        assertThat(car.id(), is(nullValue()));
        assertThat(car.model(), is(MODEL));
        assertThat(car.year(), is(YEAR));
    }

    @Test
    public void createSuccessfullyWithId() {
        Car car = Car.builder().id(CAR_ID).model(MODEL).year(YEAR).build();

        assertThat(car.id(), is(CAR_ID));
        assertThat(car.model(), is(MODEL));
        assertThat(car.year(), is(YEAR));
    }

    @Test
    public void updateId() {
        Car car = aCarWithoutId().build();

        car.created(CAR_ID);

        assertThat(car.id(), is(CAR_ID));
    }

    @Test
    public void updateCar() {
        Car car = aCarWithId().build();
        Model newModel = new Model(new Make("New Make"), "New Model");
        Year newYear = Year.of(999);
        assertThat(car.model(), not(newModel));
        assertThat(car.year(), not(newYear));

        car.update(newModel, newYear);

        assertThat(car.id(), is(CAR_ID));
        assertThat(car.model(), is(newModel));
        assertThat(car.year(), is(newYear));
    }

    @Test
    public void throwsExceptionWhenUpdatingWithNullModel() {
        Car car = aCarWithId().build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> car.update(null, car.year()));

        assertThat(exception.getMessage(), is("Car model is required."));
    }

    @Test
    public void throwsExceptionWhenUpdatingWithNullYear() {
        Car car = aCarWithId().build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> car.update(car.model(), null));

        assertThat(exception.getMessage(), is("Car year is required."));
    }

    @Test
    public void throwsExceptionWhenCreatingWithNullModel() {
        Car.Builder builder = aCarWithoutId().model(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                builder::build);

        assertThat(exception.getMessage(), is("Car model is required."));
    }

    @Test
    public void throwsExceptionWhenCreatingWithNullYear() {
        Car.Builder builder = aCarWithoutId().year(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                builder::build);

        assertThat(exception.getMessage(), is("Car year is required."));
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(Car.class).usingGetClass().verify();
    }
}