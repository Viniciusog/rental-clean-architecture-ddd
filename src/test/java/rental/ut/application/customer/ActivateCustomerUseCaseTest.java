package rental.ut.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import rental.application.AppTransaction;
import rental.application.customer.ActivateCustomerUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.customer.Customer;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerIsAlreadyActiveException;
import rental.model.exception.CustomerNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;

public class ActivateCustomerUseCaseTest {

    private AppTransaction transaction;
    private CustomerRepository repository;
    private ActivateCustomerUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(CustomerRepository.class);
        useCase = new ActivateCustomerUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).save(any());
    }

    @Test
    void activateCustomerWhenExistsAndNotActive() {
        Customer customer = mock(Customer.class);
        System.out.println(customer.getClass().getName());

        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        useCase.execute(CUSTOMER_ID);

        InOrder inOrder = inOrder(customer, repository);
        inOrder.verify(customer).activate();
        inOrder.verify(repository).save(customer);
    }

    @Test
    void propagateExceptionWhenCustomerIsAlreadyActive() {
        Customer customer = mock(Customer.class);
        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        doThrow(CustomerIsAlreadyActiveException.class).when(customer).activate();

        assertThrows(CustomerIsAlreadyActiveException.class, () -> {
            useCase.execute(CUSTOMER_ID);
        });

        verify(customer).activate();
        verify(repository, never()).save(any());
    }

    @Test
    void propagateExceptionWhenCustomerDoesNotExists() {
        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> useCase.execute(CUSTOMER_ID));

        verify(repository).getById(CUSTOMER_ID);
    }
}