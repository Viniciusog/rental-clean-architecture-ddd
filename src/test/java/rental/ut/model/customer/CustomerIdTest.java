package rental.ut.model.customer;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.customer.CustomerId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerIdTest {

    @Test
    public void createSuccessfully() {
        CustomerId customerId = CustomerId.of(123L);

        assertThat(customerId.value(), is(123L));
    }

    @Test
    public void throwsExceptionWhenCreatingWithNullValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CustomerId.of(null));

        assertThat(exception.getMessage(), is("value is required"));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    public void throwsExceptionWhenCreatingWithValueNotGreaterThanZero(Long value) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CustomerId.of(value));

        assertThat(exception.getMessage(), is("value must be greater than zero"));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(CustomerId.class).usingGetClass().verify();
    }
}
