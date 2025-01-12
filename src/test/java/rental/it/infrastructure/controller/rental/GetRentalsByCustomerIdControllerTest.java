package rental.it.infrastructure.controller.rental;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetRentalsByCustomerIdControllerTest extends ControllerTestBase {

    @Test
    public void getRentalsByCustomerIdSucessfully() throws Exception {
        mockMvc.perform(get("/rental/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "customerId": 1,
                                "carId": 2,
                                "startTime": "2025-01-01T10:00:00Z",
                                "endTime": "2025-01-07T10:00:00Z",
                                "totalPrice": 400.00
                            },
                            {
                                "id": 2,
                                "customerId": 1,
                                "carId": 1,
                                "startTime": "2025-02-01T10:00:00Z",
                                "endTime": "2025-02-08T10:00:00Z",
                                "totalPrice": 400.00
                            }
                        ]
                        """));
    }
}
