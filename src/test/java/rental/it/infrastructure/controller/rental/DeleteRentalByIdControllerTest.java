package rental.it.infrastructure.controller.rental;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

public class DeleteRentalByIdControllerTest extends ControllerTestBase {

    @Test
    void mustDeleteRentalSuccessfully() throws Exception {
        mockMvc.perform(get("/rental/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        mockMvc.perform(delete("/rental/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/rental/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsNotFoundWhenRentalWasNotFound() throws Exception {
        mockMvc.perform(get("/rental/999"))
                .andExpect(status().isNotFound());
    }
}