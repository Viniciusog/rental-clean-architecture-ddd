package rental.ut.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import rental.application.AppTransaction;
import rental.application.customer.DeactivateCustomerUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.customer.Customer;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerIsAlreadyInactiveException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;

public class DeactivateCustomerUseCaseTest {

    private AppTransaction transaction;
    private CustomerRepository repository;
    private DeactivateCustomerUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(CustomerRepository.class);
        useCase = new DeactivateCustomerUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).save(any());
    }

    @Test
    public void deactivateActiveCustomer() {
        Customer customer = mock(Customer.class);
        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        useCase.execute(CUSTOMER_ID);

        InOrder inOrder = inOrder(customer, repository);
        inOrder.verify(customer).deactivate();
        inOrder.verify(repository).save(customer);
    }

    @Test
    void throwsExceptionWhenCustomerIsAlreadyInactive() {
        Customer customer = mock(Customer.class);
        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        doThrow(CustomerIsAlreadyInactiveException.class).when(customer).deactivate();

        assertThrows(CustomerIsAlreadyInactiveException.class, () -> {
            useCase.execute(CUSTOMER_ID);
        });

        verify(customer).deactivate();
        verify(repository, never()).save(any());
    }
}