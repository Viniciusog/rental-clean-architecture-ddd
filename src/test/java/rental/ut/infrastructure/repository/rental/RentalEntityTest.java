package rental.ut.infrastructure.repository.rental;

import org.junit.jupiter.api.Test;
import rental.infrastructure.repository.rental.RentalEntity;
import rental.model.car.CarId;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;

public class RentalEntityTest {

    @Test
    void mustCreateEntity() {
        Rental rental = Rental.builder()
                .id(RentalId.of(1L))
                .customerId(CustomerId.of(1L))
                .carId(CarId.of(123L))
                .timeRange(RENTAL_TIME_RANGE)
                .totalPrice(BigDecimal.valueOf(200.00D))
                .build();

        RentalEntity entity = new RentalEntity(rental);
        Rental rentalFromEntity = entity.toRental();

        assertThat(rental, is(rentalFromEntity));
        assertThat(rental.id(), is(rentalFromEntity.id()));
    }
}
