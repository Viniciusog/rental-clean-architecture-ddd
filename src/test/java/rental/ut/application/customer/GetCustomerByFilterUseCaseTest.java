package rental.ut.application.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.customer.GetCustomerByFilterUseCase;
import rental.model.customer.Customer;
import rental.model.customer.CustomerFilter;
import rental.model.customer.CustomerRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static rental.fixture.CustomerFixture.aCustomerWithId;
import static org.hamcrest.Matchers.is;

public class GetCustomerByFilterUseCaseTest {

    private CustomerRepository repository;
    private GetCustomerByFilterUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        repository = mock(CustomerRepository.class);
        useCase = new GetCustomerByFilterUseCase(repository);
    }

    @Test
    void mustGetCustomersByFilter() {
        CustomerFilter filter = new CustomerFilter("Anna", null);
        List<Customer> expectedResult = List.of(aCustomerWithId().build());
        when(repository.getByFilter(filter)).thenReturn(expectedResult);

        List<Customer> retrievedCustomers = useCase.execute(filter);

        verify(repository).getByFilter(filter);
        assertThat(retrievedCustomers, is(expectedResult));
    }
}