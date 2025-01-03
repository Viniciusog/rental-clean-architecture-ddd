package rental.ut.model.Rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.model.rental.RentalPriceCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RentalPriceCalculatorTest {

    private RentalPriceCalculator calculator;

    @BeforeEach
    void beforeEach() {
        calculator = new RentalPriceCalculator();
    }

    @Test
    void calculateRentalPriceSuccessfully() {
        BigDecimal carDailyPrice = BigDecimal.valueOf(100.50);
        BigDecimal expectedRentalPrice = BigDecimal.valueOf(2).multiply(carDailyPrice)
                .setScale(2, RoundingMode.HALF_EVEN);
        LocalDate initialDate = LocalDate.of(2025, 01, 01);
        LocalDate endDate = LocalDate.of(2025, 01, 02);

        BigDecimal result = calculator.execute(carDailyPrice, initialDate, endDate);

        assertThat(result, is(expectedRentalPrice));
    }

    @Test
    void calculateSameDayRentalPriceSuccessfully() {
        BigDecimal carDailyPrice = BigDecimal.valueOf(100.50);
        BigDecimal expectedRentalPrice = BigDecimal.valueOf(1).multiply(carDailyPrice)
                .setScale(2, RoundingMode.HALF_EVEN);
        LocalDate initialDate = LocalDate.of(2025, 01, 01);
        LocalDate endDate = LocalDate.of(2025, 01, 01);

        BigDecimal result = calculator.execute(carDailyPrice, initialDate, endDate);

        assertThat(result, is(expectedRentalPrice));
    }
}