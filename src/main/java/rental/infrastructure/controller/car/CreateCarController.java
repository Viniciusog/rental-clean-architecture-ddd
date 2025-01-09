package rental.infrastructure.controller.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.car.CreateCarUseCase;
import rental.infrastructure.controller.car.dto.CreateCarRequest;
import rental.infrastructure.controller.car.dto.CreateCarResponse;
import rental.model.car.CarId;
import rental.model.car.Make;
import rental.model.car.Model;

import java.math.BigDecimal;
import java.time.Year;

@RestController
@RequestMapping("/car")
public class CreateCarController {

    @Autowired
    private CreateCarUseCase createCarUseCase;

    @PostMapping
    public ResponseEntity<CreateCarResponse> post(@RequestBody CreateCarRequest request) {
        Make make = new Make(request.make());
        Model model = new Model(make, request.model());
        Year year = Year.of(request.year());
        BigDecimal dailyPrice = request.dailyPrice();

        CarId carId = createCarUseCase.execute(model, year, dailyPrice);
        return new ResponseEntity<>(CreateCarResponse.of(carId), HttpStatus.CREATED);
    }
}
