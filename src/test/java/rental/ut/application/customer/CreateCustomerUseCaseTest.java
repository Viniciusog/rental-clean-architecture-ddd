package rental.ut.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import rental.application.AppTransaction;
import rental.application.customer.CreateCustomerUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static rental.fixture.CustomerFixture.*;

public class CreateCustomerUseCaseTest {

    private AppTransaction transaction;
    private CustomerRepository repository;
    private CreateCustomerUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(CustomerRepository.class);
        useCase = new CreateCustomerUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).save(any());
    }

    @Test
    void mustCreateCustomerSuccessfully() {
        Customer expectedCustomerAfterRepositoryExecution = Customer.builder()
                .name(CUSTOMER_NAME)
                .email(EMAIL)
                .id(CUSTOMER_ID)
                .build();
        doAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            customer.created(CUSTOMER_ID);
            return null;
        }).when(repository).save(any(Customer.class));

        CustomerId id = useCase.execute(CUSTOMER_NAME, EMAIL);

        verify(repository).save(expectedCustomerAfterRepositoryExecution);
        assertThat(id, is(CUSTOMER_ID));
    }
}
