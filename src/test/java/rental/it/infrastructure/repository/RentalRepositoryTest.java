package rental.it.infrastructure.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import rental.infrastructure.repository.rental.RentalRepositoryAdapter;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.exception.RentalNotFoundException;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                .initialDate(LocalDate.of(2025, 02, 01))
                .endDate(LocalDate.of(2025, 02, 05))
                .totalPrice(BigDecimal.valueOf(500.00))
                .build();

        RentalId id = repository.create(rental);
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
}