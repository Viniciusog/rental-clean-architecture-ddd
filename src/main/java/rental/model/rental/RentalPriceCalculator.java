package rental.model.rental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalPriceCalculator {

    public BigDecimal execute(BigDecimal dailyPrice,
                                           LocalDate initialDate,
                                           LocalDate endDate) {
        long days = ChronoUnit.DAYS.between(initialDate, endDate) + 1;
        return dailyPrice.multiply(BigDecimal.valueOf(days)).setScale(2, RoundingMode.HALF_EVEN);
    }
}