package rental.infrastructure.controller.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rental.application.car.UpdateCarUseCase;
import rental.model.car.CarId;

@RestController
@RequestMapping("/car")
public class UpdateCarController {

    @Autowired
    private UpdateCarUseCase updateCarUseCase;

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> update(@PathVariable Long id, @RequestBody UpdateCarRequest request) {
        updateCarUseCase.execute(
                CarId.of(id),
                request.typedModel(),
                request.typedYear(),
                request.dailyPrice());
        return ResponseEntity.noContent().build();
    }
}
