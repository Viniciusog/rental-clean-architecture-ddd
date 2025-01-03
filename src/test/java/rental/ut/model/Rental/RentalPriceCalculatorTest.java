package rental.ut.model.Rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import rental.model.car.Car;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotFoundException;
import rental.model.rental.DateTimeRange;
import rental.model.rental.RentalPriceCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.CAR_ID;
import static rental.fixture.CarFixture.aCarWithId;
import static rental.fixture.RentalFixture.RENTAL_TIME_RANGE;

public class RentalPriceCalculatorTest {

    private RentalPriceCalculator calculator;
    private CarRepository carRepository;

    @BeforeEach
    void beforeEach() {
        carRepository = mock(CarRepository.class);
        calculator = new RentalPriceCalculator(carRepository);
    }

    @Test
    void calculateRentalPriceSuccessfullyForMultipleDays() {
        BigDecimal carDailyPrice = BigDecimal.valueOf(100.50);
        when(carRepository.getById(CAR_ID))
                .thenReturn(Optional.of(aCarWithId().dailyPrice(carDailyPrice).build()));
        BigDecimal expectedRentalPrice = BigDecimal.valueOf(3).multiply(carDailyPrice)
                .setScale(2, RoundingMode.HALF_EVEN);
        Instant startTime = LocalDateTime.of(2025, 1, 1, 6, 0, 0).toInstant(ZoneOffset.UTC);
        Instant endTime = LocalDateTime.of(2025, 1, 3, 6, 0, 0).toInstant(ZoneOffset.UTC);

        BigDecimal result = calculator.execute(CAR_ID, DateTimeRange.of(startTime, endTime));

        verify(carRepository).getById(CAR_ID);
        assertThat(result, is(expectedRentalPrice));
    }

    @Test
    void calculateRentalPriceSuccessfullyForOneDay() {
        BigDecimal carDailyPrice = BigDecimal.valueOf(100.50);
        when(carRepository.getById(CAR_ID))
                .thenReturn(Optional.of(aCarWithId().dailyPrice(carDailyPrice).build()));
        BigDecimal expectedRentalPrice = BigDecimal.valueOf(1).multiply(carDailyPrice)
                .setScale(2, RoundingMode.HALF_EVEN);
        Instant startTime = LocalDateTime.of(2025, 1, 1, 6, 0, 0).toInstant(ZoneOffset.UTC);
        Instant endTime = LocalDateTime.of(2025, 1, 1, 23, 0, 0).toInstant(ZoneOffset.UTC);

        BigDecimal result = calculator.execute(CAR_ID, DateTimeRange.of(startTime, endTime));

        verify(carRepository).getById(CAR_ID);
        assertThat(result, is(expectedRentalPrice));
    }

    @Test
    void throwsExceptionWhenCarWasNotFound() {
        when(carRepository.getById(CAR_ID)).thenReturn(Optional.empty());

        CarNotFoundException exception = assertThrows(CarNotFoundException.class, () -> {
            calculator.execute(CAR_ID, RENTAL_TIME_RANGE);
        });

        verify(carRepository).getById(CAR_ID);
        assertThat(exception.getMessage(), is("Car not found with id: " + CAR_ID.value()));
    }
}