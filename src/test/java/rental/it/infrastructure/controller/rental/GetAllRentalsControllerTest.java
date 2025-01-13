package rental.it.infrastructure.controller.rental;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllRentalsControllerTest extends ControllerTestBase {

    @Test
    public void getAllRentalsSuccessfully() throws Exception {
        mockMvc.perform(get("/rental"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "customerId": 1,
                                "carId": 2,
                                "startTime": "2025-01-01T10:00:00Z",
                                "endTime": "2025-01-07T10:00:00Z",
                                "totalPrice": 2400.00
                            },
                            {
                                "id": 2,
                                "customerId": 1,
                                "carId": 1,
                                "startTime": "2025-02-01T10:00:00Z",
                                "endTime": "2025-02-08T10:00:00Z",
                                "totalPrice": 400.00
                            },
                            {
                                "id": 3,
                                "customerId": 2,
                                "carId": 1,
                                "startTime": "2025-01-04T10:00:00Z",
                                "endTime": "2025-01-10T10:00:00Z",
                                "totalPrice": 500.00
                            },
                            {
                                "id": 4,
                                "customerId": 3,
                                "carId": 3,
                                "startTime": "2025-01-05T10:00:00Z",
                                "endTime": "2025-01-08T10:00:00Z",
                                "totalPrice": 350.00
                            },
                            {
                                "id": 5,
                                "customerId": 3,
                                "carId": 3,
                                "startTime": "2025-02-01T10:00:00Z",
                                "endTime": "2025-02-10T10:00:00Z",
                                "totalPrice": 1000.00
                            },
                            {
                                "id": 6,
                                "customerId": 2,
                                "carId": 3,
                                "startTime": "2025-02-15T10:00:00Z",
                                "endTime": "2025-02-20T10:00:00Z",
                                "totalPrice": 500.00
                            }
                        ]
                        """));
    }
}