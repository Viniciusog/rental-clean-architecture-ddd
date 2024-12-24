package rental.ut.model.car;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.car.Make;
import rental.model.car.Model;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModelTest {

    @Test
    void createSuccessfully() {
        Make make = new Make("Ford");
        String name = "Ka";

        Model model = new Model(make, name);
        assertThat(model.make(), is(make));
        assertThat(model.name(), is(name));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void throwsExceptionWhenModelNameIsBlank(String modelName) {
        Make make = new Make("Ford");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Model(make, modelName));

        assertThat(exception.getMessage(), is("Model name is required."));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(Model.class).verify();
    }


    @Test
    void equalsAndHashCodeIgnoringCase() {
        Make make = new Make("Any Make");
        Model lower = new Model(make, "name");
        Model upper = new Model(make, "NAME");

        assertThat(lower, is(upper));
        assertThat(lower.hashCode(), is(upper.hashCode()));
    }


}