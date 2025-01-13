package rental.infrastructure.controller.rental.dto;

import java.time.Instant;

public record UpdateRentalRequest(Instant startTime, Instant endTime) {
}