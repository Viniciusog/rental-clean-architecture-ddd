package ut.model.car;
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
        IllegalArgumentException expection =
                assertThrows(IllegalArgumentException.class,
                () -> new Make(value));

        assertThat(expection.getMessage(), is("Make value is required."));
    }
}
