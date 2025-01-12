package rental.infrastructure.controller.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rental.application.rental.GetRentalByIdUseCase;
import rental.infrastructure.controller.rental.dto.RentalResponse;
import rental.model.rental.RentalId;

@RestController
@RequestMapping("/rental")
public class GetRentalByIdController {

    @Autowired
    private GetRentalByIdUseCase useCase;

    @RequestMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Long id) {
        RentalResponse response = RentalResponse.of(useCase.execute(RentalId.of(id)));
        return ResponseEntity.ok(response);
    }
}