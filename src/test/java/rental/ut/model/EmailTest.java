package rental.ut.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.model.Email;
import rental.model.exception.InvalidEmailException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "name@server",
            "name@server.com",
            "name@server.com.br",
            "name.etc@server.com"
    })
    void mustCreateSuccessfully(String address) {
        Email email = Email.of(address);

        assertThat(email.address(), is(address));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "name",
            "name@",
            "server.com",
            "@server.com",
            "Name <user@example>"
    })
    void throwsExceptionWhenCreatingWithInvalidAddress(String address) {
        InvalidEmailException exception = assertThrows(InvalidEmailException.class,() -> Email.of(address));

        assertThat(exception.getMessage(), is("Invalid email address: " + address));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(Email.class).usingGetClass().verify();
    }
}