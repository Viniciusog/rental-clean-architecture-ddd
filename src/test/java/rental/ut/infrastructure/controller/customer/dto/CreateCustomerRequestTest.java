package rental.ut.infrastructure.controller.customer.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.customer.dto.CreateCustomerRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateCustomerRequestTest {

    @Test
    public void mustCreateSuccessfully() {
        CreateCustomerRequest request = new CreateCustomerRequest("Name", "email@server.com");

        assertThat(request.name(), is("Name"));
        assertThat(request.email(), is("email@server.com"));
    }
}
