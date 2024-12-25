package rental.ut.model.customer;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.customer.CustomerName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerNameTest {

    @Test
    public void createSuccessfully() {
        CustomerName customerName = CustomerName.of("New name");

        assertThat(customerName.value(), is("New name"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", ""})
    public void throwsExceptionWhenValueIsBlank(String value) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CustomerName.of(value));

        assertThat(exception.getMessage(), is("value is required"));
    }

    @Test
    public void throwsExceptionWhenValueSizeIsLessThanTwoCharacters() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CustomerName.of("a"));

        assertThat(exception.getMessage(), is("value must have two or more characters"));
    }

    @Test
    public void equalsAndHashCode() {
        EqualsVerifier.forClass(CustomerName.class).usingGetClass().verify();
    }

    @Test
    public void equalsAndHashCodeIgnoringCase() {
        CustomerName lower = CustomerName.of("New Name");
        CustomerName upper = CustomerName.of("NEW NAME");

        assertThat(lower, is(upper));
        assertThat(lower.hashCode(), is(upper.hashCode()));
    }
}