package rental.infrastructure.controller.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rental.application.car.GetCarAvailabilityUseCase;
import rental.infrastructure.controller.car.dto.GetCarAvailabilityResponse;
import rental.model.car.CarId;
import rental.model.rental.DateTimeRange;

import java.time.Instant;

@RestController
@RequestMapping("/car")
public class GetCarAvailabilityController {

    @Autowired
    private GetCarAvailabilityUseCase getCarAvailabilityUseCase;

    @GetMapping("/availability/{id}")
    public ResponseEntity<GetCarAvailabilityResponse> getCarAvailability(@PathVariable Long id,
                                                                         @RequestParam Instant startTime,
                                                                         @RequestParam Instant endTime) {
        boolean available = getCarAvailabilityUseCase.execute(CarId.of(id), DateTimeRange.of(startTime,endTime));
        return ResponseEntity.ok(new GetCarAvailabilityResponse(available));
    }

}
