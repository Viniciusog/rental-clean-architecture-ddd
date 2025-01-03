package rental.it.infrastructure.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import rental.infrastructure.repository.rental.RentalRepositoryAdapter;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.exception.RentalNotFoundException;
import rental.model.rental.DateTimeRange;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;

@DataJpaTest
@Import(RentalRepositoryAdapter.class)
public class RentalRepositoryTest {

    @Autowired
    private RentalRepository repository;

    @Test
    void mustCreateSuccessfully() {
        Rental rental = Rental.builder()
                .carId(CarId.of(1L))
                .customerId(CustomerId.of(1L))
                .timeRange(RENTAL_TIME_RANGE)
                .totalPrice(BigDecimal.valueOf(500.00))
                .build();

        RentalId id = repository.save(rental);
        Rental rentalRetrieved = repository.getById(rental.id()).get();

        assertThat(rental, is(rentalRetrieved));
        assertThat(id, is(rental.id()));
    }

    @Test
    void mustGetRentalsByCustomerId() {
        CustomerId customerId = CustomerId.of(1L);

        List<Rental> rentals = repository.getByCustomerId(customerId);
        System.out.println(rentals);
        assertThat(rentals.size(), is(greaterThanOrEqualTo(2)));
    }

    @Test
    void deleteByIdSuccessfully() {
        RentalId rentalId = RentalId.of(1L);
        assertThat(repository.getById(rentalId).isPresent(), is(true));

        repository.deleteById(rentalId);

        assertThat(repository.getById(rentalId).isPresent(), is(false));
    }

    @Test
    void deleteThrowsExceptionWhenRentalWasNotFound() {
        RentalId rentalId = RentalId.of(999L);
        assertThat(repository.getById(rentalId).isPresent(), is(false));

        RentalNotFoundException exception = assertThrows(RentalNotFoundException.class, () -> {
            repository.deleteById(rentalId);
        });

        assertThat(exception.getMessage(), is("Rental not found with id: " + rentalId.value()));
    }

    @Test
    void mustGetAllRentals() {
        List<Rental> rentals = repository.getAll();

        assertThat(rentals.size(), is(greaterThanOrEqualTo(4)));
    }

    @Test
    void mustGetByCarIdAndDateRange() {
        CarId carId = CarId.of(1L);
        DateTimeRange timeRangeToSearch = DateTimeRange.of(
                LocalDateTime.of(2025, 1, 4, 10, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 8, 10, 0, 0).toInstant(ZoneOffset.UTC));
        LocalDateTime startTimeFirstRental =
                LocalDateTime.of(2025, 1, 4, 10, 0, 0);
        LocalDateTime endTimeFirstRental =
                LocalDateTime.of(2025, 1, 10, 10, 0, 0);
        Rental firstRental = Rental.builder()
                .id(RentalId.of(3L))
                .customerId(CustomerId.of(2L))
                .carId(carId)
                .timeRange(DateTimeRange.of(
                    startTimeFirstRental.toInstant(ZoneOffset.UTC),
                    endTimeFirstRental.toInstant(ZoneOffset.UTC)))
                .totalPrice(BigDecimal.valueOf(500.00))
                .build();
        LocalDateTime startTimeSecondRental =
                LocalDateTime.of(2025, 2, 1, 10, 0, 0);
        LocalDateTime endTimeSecondRental =
                LocalDateTime.of(2025, 2, 8, 10, 0, 0);
        Rental secondRental = Rental.builder()
                .id(RentalId.of(2L))
                .customerId(CustomerId.of(1L))
                .carId(carId)
                .timeRange(DateTimeRange.of(
                        startTimeSecondRental.toInstant(ZoneOffset.UTC),
                        endTimeSecondRental.toInstant(ZoneOffset.UTC)))
                .totalPrice(BigDecimal.valueOf(400.00))
                .build();

        List<Rental> rentals = repository.getByCarIdAndDateInterval(carId, timeRangeToSearch);


        assertThat(rentals.size(), is(greaterThanOrEqualTo(2)));
        assertThat(rentals, containsInAnyOrder(firstRental, secondRental));
    }
}