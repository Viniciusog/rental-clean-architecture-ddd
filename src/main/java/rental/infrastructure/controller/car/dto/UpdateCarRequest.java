package rental.infrastructure.controller.car.dto;

import rental.model.car.Make;
import rental.model.car.Model;
import java.math.BigDecimal;
import java.time.Year;

public record UpdateCarRequest(
        String make,
        String model,
        Short year,
        BigDecimal dailyPrice) {

    public Model typedModel() {
        return new Model(new Make(make), model);
    }

    public Year typedYear() {
        return year == null ? null : Year.of(year);
    }
}