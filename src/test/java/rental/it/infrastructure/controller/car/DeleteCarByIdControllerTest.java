package rental.it.infrastructure.controller.car;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteCarByIdControllerTest extends ControllerTestBase {

    @Test
    public void mustDeleteCarById() throws Exception {
        mockMvc.perform(get("/car/4"))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.id", is(1)));

        mockMvc.perform(delete("/car/4")).andExpect(status().isNoContent());

        mockMvc.perform(get("/car/4")).andExpect(status().isNotFound());
    }

    @Test
    public void deleteMustReturnNotFound() throws Exception {
        mockMvc.perform(delete("/car/999")).andExpect(status().isNotFound());
    }
}