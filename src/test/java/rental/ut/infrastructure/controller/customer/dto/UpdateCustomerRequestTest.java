package rental.ut.infrastructure.controller.customer.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.customer.dto.UpdateCustomerRequest;
import rental.model.Email;
import rental.model.customer.CustomerName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateCustomerRequestTest {

    @Test
    void mustCreateSuccessfully() {
        UpdateCustomerRequest request = new UpdateCustomerRequest(
                "Customer name",
                "customeremail@server.com"
        );

        assertThat(request.name(), is("Customer name"));
        assertThat(request.email(), is("customeremail@server.com"));
        assertThat(request.typedName(), is(CustomerName.of("Customer name")));
        assertThat(request.typedEmail(), is(Email.of("customeremail@server.com")));
    }

    @Test
    void typedNamePropagatesExceptionWhenNameIsNull() {
        UpdateCustomerRequest request = new UpdateCustomerRequest(null, "customeremail@server.com");

        assertThrows(IllegalArgumentException.class, () -> {
            CustomerName customerName = request.typedName();
        });
    }

    @Test
    void typedEmailPropagatesExceptionWhenEmailIsNull() {
        UpdateCustomerRequest request = new UpdateCustomerRequest("Customer Name", null);

        assertThrows(IllegalArgumentException.class, () -> {
            Email email = request.typedEmail();
        });
    }
}