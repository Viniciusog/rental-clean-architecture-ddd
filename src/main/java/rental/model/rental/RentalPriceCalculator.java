package rental.model.rental;

import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalPriceCalculator {

    private CarRepository carRepository;

    public RentalPriceCalculator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public BigDecimal execute(CarId carId, DateTimeRange timeRange) {

        Car car = carRepository.getById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));

        BigDecimal dailyPrice = car.dailyPrice();
        long days = ChronoUnit.DAYS.between(timeRange.start(), timeRange.end()) + 1;
        return dailyPrice.multiply(BigDecimal.valueOf(days)).setScale(2, RoundingMode.HALF_EVEN);
    }
}