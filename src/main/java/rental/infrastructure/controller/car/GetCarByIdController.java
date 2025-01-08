package rental.infrastructure.controller.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.car.GetCarByIdUseCase;
import rental.model.car.Car;
import rental.model.car.CarId;

@RestController
@RequestMapping("/car")
public class GetCarByIdController {

    @Autowired
    private GetCarByIdUseCase getCarByIdUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getById(@PathVariable Long id) {
        Car car = getCarByIdUseCase.execute(CarId.of(id));
        return ResponseEntity.ok(CarResponse.of(car));
    }
}
