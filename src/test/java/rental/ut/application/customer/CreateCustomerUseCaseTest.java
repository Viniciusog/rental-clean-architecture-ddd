package rental.ut.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.AppTransaction;
import rental.application.customer.CreateCustomerUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.customer.Customer;
import rental.model.customer.CustomerRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static rental.fixture.CustomerFixture.CUSTOMER_NAME;
import static rental.fixture.CustomerFixture.EMAIL;

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
        Customer expectedCustomer = Customer.builder().name(CUSTOMER_NAME).email(EMAIL).build();

        useCase.execute(CUSTOMER_NAME, EMAIL);

        verify(repository).save(expectedCustomer);
    }
}
