package rental.infrastructure.controller.customer;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.customer.DeactivateCustomerUseCase;
import rental.model.customer.CustomerId;

@RestController
@RequestMapping("/customer")
public class DeactivateCustomerController {

    @Autowired
    private DeactivateCustomerUseCase deactivateCustomerUseCase;

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCustomer(@PathVariable Long id) {
        deactivateCustomerUseCase.execute(CustomerId.of(id));
        return ResponseEntity.noContent().build();
    }
}