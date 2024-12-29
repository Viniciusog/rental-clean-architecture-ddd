package rental.it.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rental.application.AppTransaction;
import rental.model.customer.Customer;
import rental.model.customer.CustomerRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static rental.fixture.CustomerFixture.aCustomerWithoutId;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AppTransactionTest {

    @Autowired
    private AppTransaction transaction;

    @Autowired
    private CustomerRepository repository;

    @Test
    void mustCommitTransaction() {
        Customer customer = aCustomerWithoutId().build();

        transaction.execute(() -> repository.save(customer));

        assertThat(customer.id().value(), notNullValue());
        assertThat(repository.getById(customer.id()).isPresent(), is(true));
    }

    @Test
    void mustRollbackTransaction() {
        Customer customer = aCustomerWithoutId().build();

        RuntimeException exception = assertThrows(RuntimeException.class,
            () ->
                transaction.execute(() -> {
                    repository.save(customer);
                    throw new RuntimeException("message");
                })
        );

        assertThat(exception.getMessage(), is("message"));
        assertThat(repository.getById(customer.id()).isPresent(), is(false));
    }
}