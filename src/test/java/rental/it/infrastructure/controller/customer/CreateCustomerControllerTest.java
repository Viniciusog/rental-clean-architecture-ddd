package rental.it.infrastructure.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import rental.infrastructure.controller.customer.dto.CreateCustomerResponse;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class CreateCustomerControllerTest extends ControllerTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void mustCreateSuccessfully() throws Exception {
        String json = "{\"name\": \"Customer name\", \"email\": \"email@server.com\"}";

        MvcResult result = mockMvc.perform(
                post("/customer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        CreateCustomerResponse response = objectMapper.readValue(jsonResponse, CreateCustomerResponse.class);

        mockMvc.perform(get("/customer/"+response.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(response.id().intValue())))
                .andExpect(jsonPath("$.name", is("Customer name")))
                .andExpect(jsonPath("$.email", is("email@server.com")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    void createCustomerReturnsBadRequestWhenNameIsNull() throws Exception {
        String json = "{\"name\": null, \"email\": \"email@server.com\"}";

        mockMvc.perform(post("/customer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}