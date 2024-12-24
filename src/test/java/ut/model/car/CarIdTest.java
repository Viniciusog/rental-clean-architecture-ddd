package ut.model.car;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.car.CarId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarIdTest {

    @Test
    public void createSuccessfully() {
        CarId carId = CarId.of(123L);

        assertThat(carId.value(), is(123L));
    }

    @Test
    public void throwsExceptionWhenCreatingWithNullValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CarId.of(null)
        );

        assertThat(exception.getMessage(), is("value is required"));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    public void throwsExceptionWhenCreatingWithValueNotGreaterThanZero(long value) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CarId.of(value)
        );

        assertThat(exception.getMessage(), is("value must be greater than zero"));
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(CarId.class).usingGetClass().verify();
    }
}