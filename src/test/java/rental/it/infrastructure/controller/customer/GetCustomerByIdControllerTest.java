package rental.it.infrastructure.controller.customer;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetCustomerByIdControllerTest extends ControllerTestBase {

    @Test
    public void getSucessfully() throws Exception {
        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Anna Smith")))
                .andExpect(jsonPath("$.email", is("anna.smith@server.com")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    public void getByIdMustReturnNotFound() throws Exception {
        mockMvc.perform(get("/customer/999"))
                .andExpect(status().isNotFound());
    }
}
