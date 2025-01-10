package rental.infrastructure.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.customer.DeleteCustomerUseCase;
import rental.model.customer.CustomerId;

@RestController
@RequestMapping("/customer")
public class DeleteCustomerByIdController {

    @Autowired
    private DeleteCustomerUseCase deleteCustomerUseCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteCustomerUseCase.execute(CustomerId.of(id));
        return ResponseEntity.noContent().build();
    }
}