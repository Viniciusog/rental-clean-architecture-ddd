package rental.ut.application.customer;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.configuration.IMockitoConfiguration;
import rental.application.AppTransaction;
import rental.application.customer.DeleteCustomerUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;

public class DeleteCustomerUseCaseTest {

    private AppTransaction transaction;
    private CustomerRepository repository;
    private DeleteCustomerUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(CustomerRepository.class);
        useCase = new DeleteCustomerUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).delete(any());
    }

    @Test
    void mustDeleteCustomerWasFound() {
        useCase.execute(CUSTOMER_ID);

        verify(repository).delete(CUSTOMER_ID);
    }

    @Test
    void mustPropagateRepositoryExceptionWhenCustomerWasNotFound() {
        doThrow(CustomerNotFoundException.class).when(repository).delete(CUSTOMER_ID);

        assertThrows(CustomerNotFoundException.class, () -> {
           useCase.execute(CUSTOMER_ID);
        });

        verify(repository).delete(CUSTOMER_ID);
    }
}
