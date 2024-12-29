package rental.it.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import rental.fixture.CustomerFixture;
import rental.infrastructure.repository.customer.CustomerRepositoryAdapter;
import rental.model.Email;
import rental.model.customer.*;
import rental.model.exception.CustomerNotFoundException;

import java.util.List;

import static rental.fixture.CustomerFixture.aCustomerWithoutId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(CustomerRepositoryAdapter.class)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void mustAddAndGetById() {
        Customer customer = aCustomerWithoutId().build();

        repository.save(customer);

        Customer retrievedCustomer = repository.getById(customer.id()).orElseThrow();
        assertThat(customer.id().value(), is(notNullValue()));
        assertThat(customer, is(retrievedCustomer));
    }

    @Test
    void mustUpdate() {
        Customer customer = repository.getById(CustomerId.of(1L)).orElseThrow();
        customer.deactivate();
        customer.update(CustomerName.of("Updated name"), Email.of("updated@email.com"));

        repository.save(customer);
        Customer retrievedCustomer = repository.getById(CustomerId.of(1L)).orElseThrow();

        assertThat(customer, is(retrievedCustomer));
    }

    @Test
    void mustDelete() {
        CustomerId customerId = CustomerId.of(1L);
        assertThat(repository.getById(customerId).isPresent(), is(true));

        repository.delete(customerId);

        assertThat(repository.getById(customerId).isPresent(), is(false));
    }

    @Test
    void deleteThrowsExceptionWhenCustomerIsNotFound() {
        CustomerId customerId = CustomerId.of(999L);
        assertThat(repository.getById(customerId).isEmpty(), is(true));

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> repository.delete(customerId));

        assertThat(exception.getMessage(), is("Customer not found with id: " + customerId.value()));
    }

    @Test
    void returnCustomerWithNameStarting() {
        Customer customer1 = Customer.builder()
                .id(2L)
                .name("John Smith")
                .email("john.smith@server.com")
                .build();
        Customer customer2 = Customer.builder()
                .id(3L)
                .name("John Lennon")
                .email("john.lennon@server.com")
                .build();
        CustomerFilter filter = new CustomerFilter("John", null);


        List<Customer> customers = repository.getByFilter(filter);

        assertThat(customers.size(), is(2));
        assertThat(customers, containsInAnyOrder(customer1, customer2));
    }

    @Test
    void returnCustomerWithNameContaining() {
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("Anna Smith")
                .email("anna.smith@server.com")
                .build();

        Customer customer2 = Customer.builder()
                .id(2L)
                .name("John Smith")
                .email("john.smith@server.com")
                .build();
        CustomerFilter filter = new CustomerFilter(null, "Smith");

        List<Customer> customers = repository.getByFilter(filter);

        assertThat(customers.size(), is(2));
        assertThat(customers, containsInAnyOrder(customer1, customer2));
    }

    @Test
    void returnCustomerWithNameStartingAndContaining() {
        Customer customer = Customer.builder()
                .id(2L)
                .name("John Smith")
                .email("john.smith@server.com")
                .build();
        CustomerFilter filter = new CustomerFilter("John", "Smith");

        List<Customer> customers = repository.getByFilter(filter);

        assertThat(customers.size(), is(1));
        assertThat(customers.getFirst(), is(customer));
    }
}

