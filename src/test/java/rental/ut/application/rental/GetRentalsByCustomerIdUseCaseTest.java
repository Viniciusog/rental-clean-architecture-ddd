package rental.ut.application.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.rental.GetRentalsByCustomerIdUseCase;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;
import static rental.fixture.RentalFixture.aRentalWithId;

public class GetRentalsByCustomerIdUseCaseTest {

    private RentalRepository repository;
    private GetRentalsByCustomerIdUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        repository = mock(RentalRepository.class);
        useCase = new GetRentalsByCustomerIdUseCase(repository);
    }

    @Test
    void getRentalsSuccessfully() {
        List<Rental> expectedRentals = List.of(
                aRentalWithId().id(RentalId.of(1L)).build(),
                aRentalWithId().id(RentalId.of(2L)).build()
        );
        when(repository.getByCustomerId(CUSTOMER_ID)).thenReturn(expectedRentals);

        List<Rental> resultRentals = useCase.execute(CUSTOMER_ID);

        assertThat(resultRentals, containsInAnyOrder(resultRentals.toArray()));
        verify(repository).getByCustomerId(CUSTOMER_ID);
    }
}
