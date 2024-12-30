package rental.ut.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.customer.GetByIdCustomerUseCase;
import rental.model.customer.Customer;
import rental.model.customer.CustomerRepository;
import rental.model.exception.CustomerNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;

import static org.hamcrest.Matchers.is;
import static rental.fixture.CustomerFixture.aCustomerWithId;

public class GetByIdCustomerUseCaseTest {

    private CustomerRepository repository;
    private GetByIdCustomerUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        repository = mock(CustomerRepository.class);
        useCase = new GetByIdCustomerUseCase(repository);
    }

    @Test
    void mustGetCustomerById() {
        Customer customer = aCustomerWithId().build();
        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        Customer retrievedCustomer = useCase.execute(CUSTOMER_ID);

        assertThat(retrievedCustomer, is(customer));
        verify(repository).getById(CUSTOMER_ID);
    }

    @Test
    void propagateExceptionWhenCustomerDoesNotExists() {
        when(repository.getById(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () ->  useCase.execute(CUSTOMER_ID));

        verify(repository).getById(CUSTOMER_ID);
    }
}