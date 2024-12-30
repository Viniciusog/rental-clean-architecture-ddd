package rental.ut.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import rental.application.AppTransaction;
import rental.application.customer.UpdateCustomerUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.customer.Customer;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.CustomerFixture.*;

public class UpdateCustomerUseCaseTest {

    private AppTransaction transaction;
    private CustomerRepository repository;
    private UpdateCustomerUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(CustomerRepository.class);
        useCase = new UpdateCustomerUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction).when(repository).save(any());
    }

    @Test
    void mustUpdateCustomer() {
        Customer customer = mock(Customer.class);
        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        useCase.execute(CUSTOMER_ID, CUSTOMER_NAME, EMAIL);

        InOrder inOrder = inOrder(customer, repository);
        inOrder.verify(customer).update(CUSTOMER_NAME, EMAIL);
        inOrder.verify(repository).save(customer);
    }

    @Test
    void throwsExceptionWhenCustomerDoesNotExists() {
        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            useCase.execute(CUSTOMER_ID, CUSTOMER_NAME, EMAIL);
        });

        verify(repository, never()).save(any());
    }
}