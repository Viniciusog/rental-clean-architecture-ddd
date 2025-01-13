package rental.infrastructure.controller.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rental.application.rental.UpdateRentalUseCase;
import rental.infrastructure.controller.rental.dto.UpdateRentalRequest;
import rental.model.rental.DateTimeRange;
import rental.model.rental.RentalId;

@RestController
@RequestMapping("/rental")
public class UpdateRentalController {

    @Autowired
    private UpdateRentalUseCase updateRentalUseCase;

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                                 @RequestBody UpdateRentalRequest request) {

        DateTimeRange timeRange = DateTimeRange.of(request.startTime(), request.endTime());
        updateRentalUseCase.execute(RentalId.of(id), timeRange);
        return ResponseEntity.noContent().build();
    }
}