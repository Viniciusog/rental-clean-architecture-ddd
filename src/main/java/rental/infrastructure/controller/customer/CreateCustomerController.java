package rental.infrastructure.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rental.application.customer.CreateCustomerUseCase;
import rental.infrastructure.controller.customer.dto.CreateCustomerRequest;
import rental.infrastructure.controller.customer.dto.CreateCustomerResponse;
import rental.model.Email;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerName;

@RestController
@RequestMapping("/customer")
public class CreateCustomerController {

    @Autowired
    private CreateCustomerUseCase createCustomerUseCase;

    @PostMapping
    public ResponseEntity<CreateCustomerResponse> create(@RequestBody CreateCustomerRequest request) {
        CustomerId customerId = createCustomerUseCase.execute(CustomerName.of(request.name()), Email.of(request.email()));
        return ResponseEntity.ok(CreateCustomerResponse.of(customerId));
    }
}