package rental.it.infrastructure.controller.customer;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;

public class DeactivateCustomerControllerTest extends ControllerTestBase {

    @Test
    public void mustDeactivateCustomer() throws Exception {
        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(true)));

        mockMvc.perform(patch("/customer/1/deactivate"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(false)));
    }

    @Test
    public void mustReturnNotFoundWhenCustomerWasNotFound() throws Exception {
        mockMvc.perform(patch("/customer/999/deactivate"))
                .andExpect(status().isNotFound());
    }
}