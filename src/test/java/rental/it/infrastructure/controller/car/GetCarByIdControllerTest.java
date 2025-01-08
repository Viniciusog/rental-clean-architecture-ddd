package rental.it.infrastructure.controller.car;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetCarByIdControllerTest extends ControllerTestBase {

    @Test
    void getById() throws Exception {
        mockMvc.perform(get("/car/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")))
                .andExpect(jsonPath("$.year", is(2024)))
                .andExpect(jsonPath("$.dailyPrice", is(120.00)));
    }

    @Test
    void getByIdMustReturnNotFound() throws Exception {
        mockMvc.perform(get("/car/999"))
                .andExpect(status().isNotFound());
    }
}