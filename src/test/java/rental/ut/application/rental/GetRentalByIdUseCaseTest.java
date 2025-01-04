package rental.ut.application.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.rental.GetRentalByIdUseCase;
import rental.model.exception.RentalNotFoundException;
import rental.model.rental.Rental;
import rental.model.rental.RentalRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static rental.fixture.RentalFixture.RENTAL_ID;
import static org.hamcrest.Matchers.is;

public class GetRentalByIdUseCaseTest {

    private RentalRepository repository;
    private GetRentalByIdUseCase useCase;

    @BeforeEach
    void beforeEach() {
        repository = mock(RentalRepository.class);
        useCase = new GetRentalByIdUseCase(repository);
    }

    @Test
    void getRentalById() {
        Rental rental = mock(Rental.class);
        when(repository.getById(RENTAL_ID)).thenReturn(Optional.of(rental));

        Rental result = useCase.execute(RENTAL_ID);

        verify(repository).getById(RENTAL_ID);
        assertThat(result, is(rental));
    }

    @Test
    void throwsExceptionWhenRentalWasNotFound() {
        when(repository.getById(RENTAL_ID)).thenReturn(Optional.empty());

        assertThrows(RentalNotFoundException.class, () -> {
            useCase.execute(RENTAL_ID);
        });

        verify(repository).getById(RENTAL_ID);
    }
}
