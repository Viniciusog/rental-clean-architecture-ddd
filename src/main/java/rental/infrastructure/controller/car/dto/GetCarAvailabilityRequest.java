package rental.infrastructure.controller.car.dto;

import java.time.Instant;

public record GetCarAvailabilityRequest(Instant startTime, Instant endTime) {
}
