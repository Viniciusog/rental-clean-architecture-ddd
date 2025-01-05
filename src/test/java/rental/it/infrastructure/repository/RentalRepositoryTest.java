package rental.it.infrastructure.repository;

import org.junit.jupiter.api.DisplayName;
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
                .carId(CarId.of(3L))
                .customerId(CustomerId.of(1L))
                .timeRange(RENTAL_TIME_RANGE)
                .totalPrice(BigDecimal.valueOf(800.00))
                .build();

        RentalId id = repository.save(rental);
        Rental rentalRetrieved = repository.getById(rental.id()).get();

        assertThat(rental, is(rentalRetrieved));
        assertThat(id, is(rental.id()));
    }

    @Test
    void mustUpdateSuccessfully() {
        Rental rental = repository.getById(RentalId.of(1L)).orElseThrow();
        DateTimeRange newDateTimeRange = DateTimeRange.of(
                LocalDateTime.of(2025, 2, 1, 6, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 10, 6, 0, 0).toInstant(ZoneOffset.UTC)
        );
        BigDecimal newTotalPrice = BigDecimal.valueOf(1000.00);
        rental.update(newDateTimeRange, newTotalPrice);

        repository.save(rental);
        Rental retrieved = repository.getById(RentalId.of(1L)).orElseThrow();

        assertThat(retrieved, is(rental));
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
    @DisplayName("Returns empty list when time range is before rental interval")
    void getByCarIdAndTimeRangeBeforeTheInterval() {
        /*  Rentals from Database, with CarId = 3
            5;3;3;"2025-02-01T10:00:00Z";"2025-02-10T10:00:00Z";1000.00
            6;2;3;"2025-02-15T10:00:00Z";"2025-02-20T10:00:00Z";500.00
        */
        DateTimeRange timeRangeToSearch = DateTimeRange.of(
                LocalDateTime.of(2025, 1, 25, 10, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 1, 9, 59, 59).toInstant(ZoneOffset.UTC)
        );

        List<Rental> rentals = repository.getByCarIdAndDateInterval(CarId.of(3L), timeRangeToSearch);

        assertThat(rentals.isEmpty(), is(true));
    }

    @Test
    @DisplayName("Should return rental list when time range ends at the start of rental interval")
    void mustGetRentalsWhenTimeRangeEndsInTheIntervalStart() {
        List<Rental> expectedRentals = List.of(RENTAL_FEBRUARY_TEN_DAYS);

        DateTimeRange timeRangeToSearch = DateTimeRange.of(
                LocalDateTime.of(2025, 1, 25, 10, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 1, 10, 0, 0).toInstant(ZoneOffset.UTC)
        );

        List<Rental> rentals = repository.getByCarIdAndDateInterval(CarId.of(3L), timeRangeToSearch);

        assertThat(rentals, is(expectedRentals));
    }

    @Test
    @DisplayName("Should return list with rental when time range is the rental interval")
    void mustGetRentalsWhenTimeRangeIsTheInterval() {
        List<Rental> expectedRentals = List.of(RENTAL_FEBRUARY_TEN_DAYS);

        DateTimeRange timeRangeToSearch = DateTimeRange.of(
                LocalDateTime.of(2025, 2, 1, 10, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 10, 10, 0, 0).toInstant(ZoneOffset.UTC)
        );

        List<Rental> rentals = repository.getByCarIdAndDateInterval(CarId.of(3L), timeRangeToSearch);

        assertThat(rentals, is(expectedRentals));
    }

    @Test
    @DisplayName("Should return rental list when time range is within rental interval")
    void mustGetRentalsWhenTimeRangeIsInsideTheInterval() {
        List<Rental> expectedRentals = List.of(RENTAL_FEBRUARY_TEN_DAYS);

        DateTimeRange timeRangeToSearch = DateTimeRange.of(
                LocalDateTime.of(2025, 2, 1, 10, 0, 1).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 10, 9, 59, 59).toInstant(ZoneOffset.UTC)
        );

        List<Rental> rentals = repository.getByCarIdAndDateInterval(CarId.of(3L), timeRangeToSearch);

        assertThat(rentals, is(expectedRentals));
    }

    @Test
    @DisplayName("Should return list with rental when time range starts at the end of rental interval")
    void mustGetRentalsWhenTimeRangeStartsInTheIntervalEnd() {
        List<Rental> expectedRentals = List.of(RENTAL_FEBRUARY_TEN_DAYS);

        DateTimeRange timeRangeToSearch = DateTimeRange.of(
                LocalDateTime.of(2025, 2, 10, 10, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 11, 6, 0, 0).toInstant(ZoneOffset.UTC)
        );

        List<Rental> rentals = repository.getByCarIdAndDateInterval(CarId.of(3L), timeRangeToSearch);

        assertThat(rentals, is(expectedRentals));
    }

    @Test
    @DisplayName("Returns empty list when time range is after rental interval")
    void getRentalsByCarIdAndTimeRangeAfterInterval() {
        List<Rental> expectedRentals = List.of();

        DateTimeRange timeRangeToSearch = DateTimeRange.of(
                LocalDateTime.of(2025, 2, 10, 10, 0, 1).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 2, 11, 6, 0, 0).toInstant(ZoneOffset.UTC)
        );

        List<Rental> rentals = repository.getByCarIdAndDateInterval(CarId.of(3L), timeRangeToSearch);

        assertThat(rentals, is(expectedRentals));
    }

    private Rental RENTAL_FEBRUARY_TEN_DAYS = Rental.builder()
            .id(RentalId.of(5L))
            .customerId(CustomerId.of(3L))
            .carId(CarId.of(3L))
            .totalPrice(BigDecimal.valueOf(1000.00))
            .timeRange(DateTimeRange.of(
                        LocalDateTime.of(2025, 2, 1, 10, 0, 0).toInstant(ZoneOffset.UTC),
                        LocalDateTime.of(2025, 2, 10, 10, 0, 0).toInstant(ZoneOffset.UTC)
                )).build();


}