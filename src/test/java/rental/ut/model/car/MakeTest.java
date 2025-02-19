package rental.ut.model.car;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.car.Make;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.is;


public class MakeTest {
    @ParameterizedTest
    @ValueSource(strings = {"Ford", "Toyota"})
    void createSuccessfully(String value) {
        Make make = new Make(value);

        assertThat(make.value(), is(value));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void throwsExceptionWhenValueIsInvalid(String value) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                () -> new Make(value));

        assertThat(exception.getMessage(), is("Make value is required."));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(Make.class).verify();
    }

    @Test
    void equalsAndHashCodeIgnoringCase() {
        Make lower = new Make("name");
        Make upper = new Make("NAME");

        assertThat(lower, is(upper));
        assertThat(lower.hashCode(), is(upper.hashCode()));
    }
}