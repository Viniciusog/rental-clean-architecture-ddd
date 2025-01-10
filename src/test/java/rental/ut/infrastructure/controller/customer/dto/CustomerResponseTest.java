package rental.ut.infrastructure.controller.customer.dto;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.customer.dto.CustomerResponse;
import rental.model.customer.Customer;

import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.CustomerFixture.aCustomerWithId;
import static org.hamcrest.Matchers.is;

public class CustomerResponseTest {

    @Test
    public void createSucessfully() {
        Customer customer = aCustomerWithId().build();

        CustomerResponse response = CustomerResponse.of(customer);

        assertThat(response.id(), is(customer.id().value()));
        assertThat(response.name(), is(customer.name().value()));
        assertThat(response.email(), is(customer.email().address()));
        assertThat(response.active(), is(customer.isActive()));
    }
}
