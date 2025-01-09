package rental.infrastructure.controller.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.car.GetAllCarsUseCase;
import rental.infrastructure.controller.car.dto.CarResponse;

import java.util.List;

@RestController
@RequestMapping("/car")
public class GetAllCarsController {

    @Autowired
    private GetAllCarsUseCase getAllCarsUseCase;

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<CarResponse> cars = getAllCarsUseCase.execute()
                .stream()
                .map(CarResponse::of).toList();
        return ResponseEntity.ok(cars);
    }
}