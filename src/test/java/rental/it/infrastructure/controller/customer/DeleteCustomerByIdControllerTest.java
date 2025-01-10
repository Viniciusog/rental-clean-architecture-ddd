package rental.it.infrastructure.controller.customer;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;

public class DeleteCustomerByIdControllerTest extends ControllerTestBase {
    @Test
    public void mustDeleteCustomer() throws Exception {
        mockMvc.perform(get("/customer/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)));

        mockMvc.perform(delete("/customer/4")).andExpect(status().isNoContent());

        mockMvc.perform(get("/customer/4"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void mustReturnNotFoundWhenCustomerWasNotFound() throws Exception {
        mockMvc.perform(delete("/customer/999")).andExpect(status().isNotFound());
    }
}
