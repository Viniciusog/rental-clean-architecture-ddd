package ut.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.Validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationTest {

    @Test
    void mustReturnValueWhenItsNotNull() {
        String value = "any";

        String result = Validation.required(value, "message");
        assertThat(value, is(result));
    }

    @Test
    void throwsExceptionWhenValueIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Validation.required(null, "message")
        );
        assertThat(exception.getMessage(), is("message"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void throwsExceptionWhenValueIsBlankString(String value) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Validation.required(value, "message")
        );

        assertThat(exception.getMessage(), is("message"));
    }
}