package rental.ut.infrastructure.repository.customer;

import org.junit.jupiter.api.Test;
import rental.infrastructure.repository.customer.CustomerEntity;
import rental.model.customer.Customer;

import static org.hamcrest.MatcherAssert.assertThat;
import static rental.fixture.CustomerFixture.aCustomerWithId;
import static org.hamcrest.Matchers.is;

public class CustomerEntityTest {

    @Test
    void mustCreateSuccessfully() {
        Customer customer = aCustomerWithId().build();

        CustomerEntity customerEntity = new CustomerEntity(customer);
        Customer customerFromEntity = customerEntity.toCustomer();

        assertThat(customerFromEntity, is(customer));
        assertThat(customer.id(), is(customerFromEntity.id()));
    }
}
