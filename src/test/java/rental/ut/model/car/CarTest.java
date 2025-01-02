package rental.ut.model.car;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import rental.model.car.Car;
import rental.model.car.Make;
import rental.model.car.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;

import static rental.fixture.CarFixture.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rental.fixture.CarFixture.*;

public class CarTest {

    @Test
    public void createSuccessfully() {
        Car car = aCarWithoutId().build();

        assertThat(car.id(), is(nullValue()));
        assertThat(car.model(), is(MODEL));
        assertThat(car.year(), is(YEAR));
        assertThat(car.dailyPrice(), is(DAILY_PRICE.setScale(2, RoundingMode.HALF_EVEN)));
    }

    @Test
    public void createSuccessfullyWithId() {
        Car car = Car.builder().id(CAR_ID).model(MODEL).year(YEAR).dailyPrice(DAILY_PRICE).build();

        assertThat(car.id(), is(CAR_ID));
        assertThat(car.model(), is(MODEL));
        assertThat(car.year(), is(YEAR));
        assertThat(car.dailyPrice(), is(DAILY_PRICE.setScale(2, RoundingMode.HALF_EVEN)));
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
        BigDecimal newDailyPrice = BigDecimal.valueOf(9999);
        assertThat(car.model(), not(newModel));
        assertThat(car.year(), not(newYear));
        assertThat(car.dailyPrice(), not(newDailyPrice.setScale(2, RoundingMode.HALF_EVEN)));

        car.update(newModel, newYear, newDailyPrice);

        assertThat(car.id(), is(CAR_ID));
        assertThat(car.model(), is(newModel));
        assertThat(car.year(), is(newYear));
        assertThat(car.dailyPrice(), is(newDailyPrice.setScale(2, RoundingMode.HALF_EVEN)));
    }

    @Test
    public void throwsExceptionWhenUpdatingWithNullModel() {
        Car car = aCarWithId().build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> car.update(null, car.year(), car.dailyPrice()));

        assertThat(exception.getMessage(), is("Car model is required."));
    }

    @Test
    public void throwsExceptionWhenUpdatingWithNullYear() {
        Car car = aCarWithId().build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> car.update(car.model(), null, car.dailyPrice()));

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
        EqualsVerifier.simple().forClass(Car.class).usingGetClass().withNonnullFields("dailyPrice").verify();
    }
}