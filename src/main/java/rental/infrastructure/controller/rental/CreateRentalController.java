package rental.infrastructure.controller.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.rental.CreateRentalUseCase;
import rental.infrastructure.controller.rental.dto.CreateRentalRequest;
import rental.infrastructure.controller.rental.dto.CreateRentalResponse;
import rental.model.rental.RentalId;

@RestController
@RequestMapping("/rental")
public class CreateRentalController {

    @Autowired
    private CreateRentalUseCase createRentalUseCase;

    @PostMapping
    public ResponseEntity<CreateRentalResponse> create(@RequestBody CreateRentalRequest request) {
        RentalId rentalId = createRentalUseCase.execute(
                request.typedCustomerId(),
                request.typedCarId(),
                request.typedDateTimeRange());
        return ResponseEntity.ok(new CreateRentalResponse(rentalId.value()));
    }
}