package rental.infrastructure.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.customer.ActivateCustomerUseCase;
import rental.model.customer.CustomerId;

@RestController
@RequestMapping("/customer")
public class ActivateCustomerController {

    @Autowired
    private ActivateCustomerUseCase activateCustomerUseCase;

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> mustActivateUser(@PathVariable Long id) {
        activateCustomerUseCase.execute(CustomerId.of(id));
        return ResponseEntity.noContent().build();
    }
}