package rental.infrastructure.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rental.application.customer.GetCustomerByFilterUseCase;
import rental.infrastructure.controller.customer.dto.CustomerResponse;
import rental.model.customer.CustomerFilter;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class GetCustomersByFilterController {

    @Autowired
    private GetCustomerByFilterUseCase getCustomerByFilterUseCase;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomerByFilter(
            @RequestParam(required = false) String nameStarting,
            @RequestParam(required = false) String nameContaining) {
        CustomerFilter customerFilter = new CustomerFilter(nameStarting, nameContaining);
        List<CustomerResponse> customers = getCustomerByFilterUseCase.execute(customerFilter)
                .stream().map(CustomerResponse::of).toList();
        return ResponseEntity.ok(customers);
    }
}
