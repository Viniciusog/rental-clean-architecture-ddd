package rental.it.infrastructure.controller.car;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GetAllCarsControllerTest extends ControllerTestBase {

    @Test
    public void mustGetAllCars() throws Exception {
        mockMvc.perform(get("/car"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    [
                        {"id":1, "make":"Toyota", "model":"Corolla", "year":2024},
                        {"id":2, "make":"Ford", "model":"Mustang", "year":2024},
                        {"id":3, "make":"Chevrolet", "model":"Onix", "year":2024},
                        {"id":4, "make":"Honda", "model":"Civic", "year":2024}
                    ]
                """));
    }
}
