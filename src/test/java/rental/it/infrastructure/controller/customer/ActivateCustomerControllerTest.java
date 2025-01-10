package rental.it.infrastructure.controller.customer;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;

public class ActivateCustomerControllerTest extends ControllerTestBase {

    @Test
    public void activateSuccessfully() throws Exception {
        mockMvc.perform(get("/customer/4")).andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(false)));

        mockMvc.perform(patch("/customer/4/activate"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/customer/4")).andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    public void returnsNotFoundWhenCustomerWasNotFound() throws Exception {
        mockMvc.perform(patch("/customer/999/activate"))
                .andExpect(status().isNotFound());
    }
}