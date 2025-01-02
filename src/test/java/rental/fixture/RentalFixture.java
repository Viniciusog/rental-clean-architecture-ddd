package rental.fixture;

import rental.model.rental.Rental;
import rental.model.rental.RentalId;

import java.math.BigDecimal;
import java.time.LocalDate;

import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;

public class RentalFixture {

    public static final Long RENTAL_ID = 123L;
    public static final BigDecimal RENTAL_TOTAL_PRICE = BigDecimal.valueOf(500.00);
    public static final LocalDate RENTAL_INITIAL_DATE = LocalDate.of(2025, 01, 01);
    public static final LocalDate RENTAL_END_DATE = LocalDate.of(2025, 01, 05);


    public static Rental.Builder aRentalWithId() {
        return builder();
    }

    public static Rental.Builder aRentalWithoutId() {
        return builder().id(null);
    }

    public static Rental.Builder builder() {
        return Rental.builder()
                .id(RentalId.of(RENTAL_ID))
                .carId(CAR_ID)
                .customerId(CUSTOMER_ID)
                .totalPrice(RENTAL_TOTAL_PRICE)
                .initialDate(RENTAL_INITIAL_DATE)
                .endDate(RENTAL_END_DATE);
    }
}