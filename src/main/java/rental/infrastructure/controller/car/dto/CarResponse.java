package rental.infrastructure.controller.car.dto;

import rental.model.car.Car;

import java.math.BigDecimal;

public record CarResponse(
        Long id,
        String make,
        String model,
        Short year,
        BigDecimal dailyPrice
) {

    public static CarResponse of(Car car) {
        return new CarResponse(
                car.id().value(),
                car.model().make().value(),
                car.model().name(),
                (short)car.year().getValue(),
                car.dailyPrice());
    }
}
