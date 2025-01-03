package rental.ut.model.Rental;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;

public class RentalTest {

    @Test
    void createSuccessfully() {
        Rental rental = aBuilderWithoutId().build();

        assertThat(rental.id(), is(nullValue()));
        assertThat(rental.customerId(), is(CUSTOMER_ID));
        assertThat(rental.carId(), is(CAR_ID));
        assertThat(rental.timeRange(), is(RENTAL_TIME_RANGE));
        assertThat(rental.totalPrice(), is(TOTAL_PRICE.setScale(2, RoundingMode.HALF_EVEN)));
    }

    @Test
    void createSuccessfullyWithId() {
        Rental rental = aBuilderWithId().build();

        assertThat(rental.id(), is(RENTAL_ID));
    }

    @Test
    void mustUpdateId() {
        Rental rental = aBuilderWithoutId().build();
        assertThat(rental.id(), is(nullValue()));

        rental.created(RENTAL_ID);

        assertThat(rental.id(), is(RENTAL_ID));
    }

    @Test
    void throwsExceptionWhenCreatingWithNullCustomerId() {
        Rental.Builder builder = aBuilderWithId().customerId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                builder::build);

        assertThat(exception.getMessage(), is("customerId is required"));
    }

    @Test
    void throwsExceptionWhenCreatingWithNullCarId() {
        Rental.Builder builder = aBuilderWithId().carId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                builder::build);

        assertThat(exception.getMessage(), is("carId is required"));
    }

    @Test
    void throwsExceptionWhenCreatingWithNullTimeRange() {
        Rental.Builder builder = aBuilderWithId().timeRange(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                builder::build);

        assertThat(exception.getMessage(), is("timeRange is required"));
    }

    @Test
    void throwsExceptionWhenCreatingWithNullTotalPrice() {
        Rental.Builder builder = aBuilderWithId().totalPrice(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                builder::build);

        assertThat(exception.getMessage(), is("totalPrice is required"));
    }

    @Test
    void throwsExceptionWhenCreatingWithNegativeTotalPrice() {
        Rental.Builder builder = aBuilderWithId().totalPrice(BigDecimal.valueOf(-0.01));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                builder::build);

        assertThat(exception.getMessage(), is("totalPrice cannot be negative"));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(Rental.class).withNonnullFields("totalPrice").verify();
    }

    private Rental.Builder aBuilderWithId() {
        return aBuilderWithoutId().id(RENTAL_ID);
    }

    private Rental.Builder aBuilderWithoutId() {
        return Rental.builder()
                .customerId(CUSTOMER_ID)
                .carId(CAR_ID)
                .timeRange(RENTAL_TIME_RANGE)
                .totalPrice(TOTAL_PRICE);
    }

    private static final RentalId RENTAL_ID = RentalId.of(1L);
    private static final CustomerId CUSTOMER_ID = CustomerId.of(2L);
    private static final CarId CAR_ID = CarId.of(3L);
    private static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(500L);

}