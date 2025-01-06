package rental.ut.application.rental;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.rental.GetRentalsByCarIdAndTimeRangeUseCase;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;
import static rental.fixture.RentalFixture.aRentalWithId;

public class GetRentalsByCarIdAndTimeRangeUseCaseTest {

    private RentalRepository repository;
    private GetRentalsByCarIdAndTimeRangeUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        repository = mock(RentalRepository.class);
        useCase = new GetRentalsByCarIdAndTimeRangeUseCase(repository);
    }

    @Test
    public void mustGetRentalsSuccessfully() {
        List<Rental> expectedRentals = List.of(
                aRentalWithId().id(RentalId.of(1L)).build(),
                aRentalWithId().id(RentalId.of(2L)).build()
        );
        when(repository.getByCarIdAndTimeRange(CAR_ID, RENTAL_TIME_RANGE))
                .thenReturn(expectedRentals);

        List<Rental> rentals = useCase.execute(CAR_ID, RENTAL_TIME_RANGE);

        assertThat(expectedRentals, is(rentals));
        verify(repository).getByCarIdAndTimeRange(CAR_ID, RENTAL_TIME_RANGE);
    }
}
