package rental.ut.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import rental.model.Password;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rental.fixture.CustomerFixture.PASSWORD;
import static org.hamcrest.Matchers.is;

public class PasswordTest {

    @Test
    void mustCreateSuccessfully() {
        Password password = PASSWORD;

        assertThat(password.value(), is(PASSWORD.value()));
    }

    @Test
    void throwsExceptionWhenCreatingWithNullValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Password.of(null);
        });

        assertThat(exception.getMessage(), is("password is required"));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(Password.class).usingGetClass().verify();
    }
}