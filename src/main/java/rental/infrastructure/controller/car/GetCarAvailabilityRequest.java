package rental.infrastructure.controller.car;

import java.time.Instant;

public record GetCarAvailabilityRequest(Instant startTime, Instant endTime) {
}
