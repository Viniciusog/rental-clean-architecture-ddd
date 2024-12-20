package ut.model;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.Make;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class MakeTest {

    @ParameterizedTest
    @ValueSource(strings = {"Ford", "Toyota"})
    void createSuccessfully(String value) {
        Make make = new Make(value);
        assertThat(make.value(), is(value));
    }
}
