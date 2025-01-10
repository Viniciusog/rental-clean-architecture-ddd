package rental.infrastructure.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.customer.GetCustomerByIdUseCase;
import rental.infrastructure.controller.customer.dto.CustomerResponse;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;

@RestController
@RequestMapping("/customer")
public class GetCustomerByIdController {

    @Autowired
    private GetCustomerByIdUseCase getCustomerByIdUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Customer customer = getCustomerByIdUseCase.execute(CustomerId.of(id));
        CustomerResponse response = CustomerResponse.of(customer);
        return ResponseEntity.ok(response);
    }
}