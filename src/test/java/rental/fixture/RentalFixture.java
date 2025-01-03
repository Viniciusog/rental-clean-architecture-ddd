package rental.fixture;

import rental.model.rental.DateTimeRange;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.CustomerFixture.CUSTOMER_ID;

public class RentalFixture {

    public static final Long RENTAL_ID = 123L;
    public static final BigDecimal RENTAL_TOTAL_PRICE = BigDecimal.valueOf(500.00);
    public static final LocalDate RENTAL_INITIAL_DATE = LocalDate.of(2025, 01, 01);
    public static final LocalDate RENTAL_END_DATE = LocalDate.of(2025, 01, 05);
    public static final Instant RENTAL_START_INSTANT =
            Instant.from(LocalDateTime.of(
                    2025, 1, 1, 6, 0, 0)
                        .toInstant(ZoneOffset.UTC));

    public static final Instant RENTAL_END_INSTANT =
            Instant.from(LocalDateTime.of(
                            2025, 1, 5, 6, 0, 0)
                    .toInstant(ZoneOffset.UTC));

    public static final DateTimeRange RENTAL_TIME_RANGE =
            DateTimeRange.of(RENTAL_START_INSTANT, RENTAL_END_INSTANT);


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
                .timeRange(RENTAL_TIME_RANGE);
    }
}