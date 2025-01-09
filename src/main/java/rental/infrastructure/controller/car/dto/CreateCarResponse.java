package rental.infrastructure.controller.car.dto;

import rental.model.car.CarId;

public record CreateCarResponse(Long id) {

    public static CreateCarResponse of(CarId carId) {
        return new CreateCarResponse(carId.value());
    }
}