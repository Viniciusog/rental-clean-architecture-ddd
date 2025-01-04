package rental.ut.application.rental;

import org.hibernate.sql.Delete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.AppTransaction;
import rental.application.rental.DeleteRentalUseCase;
import rental.fixture.AppTransactionFixture;
import rental.model.exception.RentalNotFoundException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static rental.fixture.RentalFixture.RENTAL_ID;
import static rental.fixture.RentalFixture.aRentalWithId;

public class DeleteRentalUseCaseTest {

    private AppTransaction transaction;
    private RentalRepository repository;
    private DeleteRentalUseCase useCase;

    @BeforeEach
    void beforeEach() {
        transaction = AppTransactionFixture.mockedTransaction();
        repository = mock(RentalRepository.class);
        useCase = new DeleteRentalUseCase(transaction, repository);
        AppTransactionFixture.assertThatInTransaction(transaction)
                .when(repository).deleteById(any());
    }

    @Test
    void deleteRentalSuccessfully() {
        Rental rental = mock(Rental.class);
        when(repository.getById(RENTAL_ID)).thenReturn(Optional.of(rental));

        useCase.execute(RENTAL_ID);

        verify(repository).deleteById(RENTAL_ID);
    }

    @Test
    void propagateExceptionWhenRentalWasNotFound() {
        doThrow(RentalNotFoundException.class).when(repository).deleteById(RENTAL_ID);

        assertThrows(RentalNotFoundException.class, () -> {
            useCase.execute(RENTAL_ID);
        });
    }
}
