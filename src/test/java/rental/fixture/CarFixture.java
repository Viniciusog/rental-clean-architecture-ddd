package rental.fixture;

import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.Make;
import rental.model.car.Model;

import java.math.BigDecimal;
import java.time.Year;

public class CarFixture {
    public static final CarId CAR_ID = CarId.of(1l);
    public static final Make MAKE = new Make("Ford");
    public static final Model MODEL = new Model(MAKE, "KA");
    public static final Year YEAR = Year.of(2024);
    public static final BigDecimal DAILY_PRICE = BigDecimal.valueOf(100.00);

    public CarFixture() {

    }

    public static Car.Builder aCarWithoutId() {
        return Car.builder()
                .model(MODEL)
                .year(YEAR)
                .dailyPrice(DAILY_PRICE);
    }

    public static Car.Builder aCarWithId() {
        return aCarWithoutId().id(CAR_ID);
    }
}