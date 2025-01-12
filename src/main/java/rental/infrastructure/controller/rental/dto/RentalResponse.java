package rental.infrastructure.controller.rental.dto;

import rental.model.rental.Rental;

import java.math.BigDecimal;
import java.time.Instant;

public record RentalResponse(Long id,
                             Long customerId,
                             Long carId,
                             Instant startTime,
                             Instant endTime,
                             BigDecimal totalPrice) {
    public static RentalResponse of(Rental rental) {
        return new RentalResponse(
                rental.id().value(),
                rental.customerId().value(),
                rental.carId().value(),
                rental.timeRange().start(),
                rental.timeRange().end(),
                rental.totalPrice());
    }
}
