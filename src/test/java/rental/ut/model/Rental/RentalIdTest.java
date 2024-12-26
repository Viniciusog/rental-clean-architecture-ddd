package rental.ut.model.Rental;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.rental.RentalId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RentalIdTest {

    @Test
    void createSuccessfully() {
        RentalId rentalId = RentalId.of(123L);

        assertThat(rentalId.value(), is(123L));
    }

    @Test
    void throwsExceptionWhenCreatingWithNullValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RentalId.of(null));

        assertThat(exception.getMessage(), is("value is required"));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void throwsExceptionWhenCreatingWithValueNotGreaterThanZero(Long value) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RentalId.of(value));

        assertThat(exception.getMessage(), is("value must be greater than zero"));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(RentalId.class).usingGetClass().verify();
    }
}