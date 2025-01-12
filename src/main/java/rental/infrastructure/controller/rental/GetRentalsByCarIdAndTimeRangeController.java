package rental.infrastructure.controller.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rental.application.rental.GetRentalsByCarIdAndTimeRangeUseCase;
import rental.infrastructure.controller.rental.dto.RentalResponse;
import rental.model.car.CarId;
import rental.model.rental.DateTimeRange;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/rental")
public class GetRentalsByCarIdAndTimeRangeController {

    @Autowired
    private GetRentalsByCarIdAndTimeRangeUseCase useCase;

    @GetMapping("/search")
    public ResponseEntity<List<RentalResponse>> getRentalsByCarIdAndTimeRange(
            @RequestParam(required = true) Long carId,
            @RequestParam(required = true) Instant startTime,
            @RequestParam(required = true) Instant endTime) {
        List<RentalResponse> rentals = useCase.execute(
                CarId.of(carId),
                DateTimeRange.of(startTime, endTime))
                .stream()
                .map(RentalResponse::of)
                .toList();
        return ResponseEntity.ok(rentals);
    }
}