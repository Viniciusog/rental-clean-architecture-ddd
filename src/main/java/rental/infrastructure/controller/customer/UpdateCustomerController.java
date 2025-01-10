package rental.infrastructure.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rental.application.customer.UpdateCustomerUseCase;
import rental.infrastructure.controller.customer.dto.UpdateCustomerRequest;
import rental.model.customer.CustomerId;

@RestController
@RequestMapping("/customer")
public class UpdateCustomerController {

    @Autowired
    private UpdateCustomerUseCase updateCustomerUseCase;

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateCustomerRequest request) {
        updateCustomerUseCase.execute(
                CustomerId.of(id),
                request.typedName(),
                request.typedEmail());
        return ResponseEntity.noContent().build();
    }
}
