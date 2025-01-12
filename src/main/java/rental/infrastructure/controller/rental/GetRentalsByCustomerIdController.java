package rental.infrastructure.controller.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.rental.GetRentalsByCustomerIdUseCase;
import rental.infrastructure.controller.rental.dto.RentalResponse;
import rental.model.customer.CustomerId;

import java.util.List;

@RestController
@RequestMapping("/rental")
public class GetRentalsByCustomerIdController {

    @Autowired
    private GetRentalsByCustomerIdUseCase getRentalsByCustomerIdUseCase;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RentalResponse>> getResponseByCustomerId(@PathVariable Long customerId) {
        List<RentalResponse> rentals = getRentalsByCustomerIdUseCase.execute(CustomerId.of(customerId))
                .stream().map(RentalResponse::of).toList();
        return ResponseEntity.ok(rentals);
    }
}