package rental.ut.model.customer;

import net.bytebuddy.pool.TypePool;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.fixture.CustomerFixture;
import rental.model.Email;
import rental.model.customer.Customer;
import rental.model.customer.CustomerName;
import rental.model.exception.CustomerIsAlreadyActiveException;
import rental.model.exception.CustomerIsAlreadyInactiveException;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rental.fixture.CustomerFixture.*;

public class CustomerTest {

    @Test
    void createSuccessfully() {
        Customer customer = Customer.builder()
                .name(CUSTOMER_NAME)
                .email(EMAIL)
                .build();

        assertThat(customer.id(), is(nullValue()));
        assertThat(customer.name(), is(CUSTOMER_NAME));
        assertThat(customer.email(), is(EMAIL));
        assertThat(customer.isActive(), is(true));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void createFullSuccessfully(boolean active) {
        Customer customer  = Customer.builder()
                .id(CUSTOMER_ID)
                .name(CUSTOMER_NAME)
                .email(EMAIL)
                .active(active)
                .build();

        assertThat(customer.id(), is(CUSTOMER_ID));
        assertThat(customer.name(), is(CUSTOMER_NAME));
        assertThat(customer.email(), is(EMAIL));
        assertThat(customer.isActive(), is(active));
    }

    @Test
    void throwsExceptionWhenCreatingWithNullName() {
        Customer.Builder builder = CustomerFixture.builder()
                .name((String)null);

        IllegalArgumentException exception  = assertThrows(IllegalArgumentException.class,
                () -> builder.build());

        assertThat(exception.getMessage(), is("name is required"));
    }

    @Test
    void throwsExceptionWhenCreatingWithNullActive() {
        Customer.Builder builder = CustomerFixture.builder()
                .active(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> builder.build());

        assertThat(exception.getMessage(), is("active is required"));
    }

    @Test
    void doesNotThrowsExceptionWhenCreatingWithNullEmail() {
        Customer customer = CustomerFixture.builder()
                .email((String) null).build();

        assertThat(customer.email(), is(nullValue()));
    }

    @Test
    void mustDeactivate() {
        Customer customer = anActiveCustomer().build();
        assertThat(customer.isActive(), is(true));

        customer.deactivate();

        assertThat(customer.isActive(), is(false));
    }

    @Test
    void throwsExceptionWhenDeactivatingAlreadyInactive() {
        Customer customer = anInactiveCustomer().build();
        assertThat(customer.isActive(), is(false));

        assertThrows(CustomerIsAlreadyInactiveException.class, customer::deactivate);
    }

    @Test
    void mustActivate() {
        Customer customer = anInactiveCustomer().build();
        assertThat(customer.isActive(), is(false));

        customer.activate();

        assertThat(customer.isActive(), is(true));
    }

    @Test
    void throwsExceptionWhenActivatingAlreadyActive() {
        Customer customer = anActiveCustomer().build();
        assertThat(customer.isActive(), is(true));

        assertThrows(CustomerIsAlreadyActiveException.class, customer::activate);
    }

    @Test
    void mustUpdateId() {
        Customer customer = aCustomerWithoutId().build();
        assertThat(customer.id(), is(nullValue()));

        customer.created(CUSTOMER_ID);

        assertThat(customer.id(), is(CUSTOMER_ID));
    }

    @Test
    void mustUpdate() {
        Customer customer = aCustomerWithId().build();
        assertThat(customer.name(), not("Updated"));
        assertThat(customer.email(), not("updated@server.com"));

        customer.update(CustomerName.of("Updated"),
                Email.of("updated@server.com"));

        assertThat(customer.name().value(), is("Updated"));
        assertThat(customer.email().address(), is("updated@server.com"));
    }

    @Test
    void updateThrowsExceptionWhenNameIsNull() {
        Customer customer = aCustomerWithId().build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.update(null, customer.email());
        });

        assertThat(exception.getMessage(), is("name is required"));
    }

    @Test
    void updateThrowsExceptionWhenEmailIsNull() {
        Customer customer = aCustomerWithId().build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.update(customer.name(), null);
        });

        assertThat(exception.getMessage(), is("email is required"));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(Customer.class).usingGetClass().verify();
    }
}