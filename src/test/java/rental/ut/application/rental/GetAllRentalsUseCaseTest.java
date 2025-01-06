package rental.ut.application.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.rental.GetAllRentalsUseCase;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;
import static rental.fixture.RentalFixture.aRentalWithId;

public class GetAllRentalsUseCaseTest {

    private RentalRepository repository;
    private GetAllRentalsUseCase useCase;

    @BeforeEach
    void beforeEach() {
        repository = mock(RentalRepository.class);
        useCase = new GetAllRentalsUseCase(repository);
    }

    @Test
    void mustGetallRentals() {
        List<Rental> expectedRentals = List.of(
                aRentalWithId().id(RentalId.of(1L)).build(),
                aRentalWithId().id(RentalId.of(2L)).build(),
                aRentalWithId().id(RentalId.of(3L)).build()
        );
        when(repository.getAll()).thenReturn(expectedRentals);

        List<Rental> retrievedRentals = useCase.execute();

        verify(repository).getAll();
        assertThat(retrievedRentals, containsInAnyOrder(expectedRentals.toArray()));
    }
}