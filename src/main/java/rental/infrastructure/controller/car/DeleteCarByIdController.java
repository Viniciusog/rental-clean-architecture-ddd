package rental.infrastructure.controller.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.car.DeleteCarUseCase;
import rental.model.car.CarId;

@RestController
@RequestMapping("/car")
public class DeleteCarByIdController {

    @Autowired
    private DeleteCarUseCase deleteCarUseCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        CarId carId = CarId.of(id);
        deleteCarUseCase.execute(carId);
        return ResponseEntity.noContent().build();
    }
}