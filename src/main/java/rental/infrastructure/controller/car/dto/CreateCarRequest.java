package rental.infrastructure.controller.car;

import java.math.BigDecimal;

public record CreateCarRequest(
        String make,
        String model,
        short year,
        BigDecimal dailyPrice) {

}