package rental.ut.application.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.rental.GetAllRentalUseCase;
import rental.application.rental.GetRentalByIdUseCase;
import rental.model.rental.Rental;
import rental.model.rental.RentalRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static rental.fixture.RentalFixture.aRentalWithId;

public class GetAllRentalUseCaseTest {

    private RentalRepository repository;
    private GetAllRentalUseCase useCase;

    @BeforeEach
    void beforeEach() {
        repository = mock(RentalRepository.class);
        useCase = new GetAllRentalUseCase(repository);
    }

    @Test
    void mustGetallRentals() {
        List<Rental> expected = List.of(aRentalWithId().build());
        when(repository.getAll()).thenReturn(expected);

        List<Rental> retrievedRentals = useCase.execute();

        assertThat(retrievedRentals, is(expected));
    }
}