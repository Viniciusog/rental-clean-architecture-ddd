package rental.infrastructure.controller.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.rental.GetAllRentalsUseCase;
import rental.infrastructure.controller.rental.dto.RentalResponse;

import java.util.List;

@RestController
@RequestMapping("/rental")
public class GetAllRentalsController {

    @Autowired
    private GetAllRentalsUseCase getAllRentalsUseCase;

    @GetMapping
    public ResponseEntity<List<RentalResponse>> getRentals() {
        List<RentalResponse> rentals = getAllRentalsUseCase.execute()
                .stream().map(RentalResponse::of).toList();
        return ResponseEntity.ok(rentals);
    }
}
