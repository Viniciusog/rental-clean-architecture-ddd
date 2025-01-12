package rental.infrastructure.controller.rental.dto;

import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.rental.DateTimeRange;

import java.time.Instant;

public record CreateRentalRequest(Long customerId,
                                  Long carId,
                                  Instant startTime,
                                  Instant endTime) {

    public CustomerId typedCustomerId() {
        return CustomerId.of(customerId);
    }

    public CarId typedCarId() {
        return CarId.of(carId);
    }

    public DateTimeRange typedDateTimeRange() {
        return DateTimeRange.of(startTime, endTime);
    }
}