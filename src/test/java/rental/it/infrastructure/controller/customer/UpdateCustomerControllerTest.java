package rental.it.infrastructure.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import rental.infrastructure.configuration.ExceptionResponse;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateCustomerControllerTest extends ControllerTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void mustUpdateCustomer() throws Exception {
        String json = """
                    {
                        "name": "Updated name",
                        "email": "updatedemail@server.com"
                    }
                """;

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Anna Smith")))
                .andExpect(jsonPath("$.email", is("anna.smith@server.com")))
                .andExpect(jsonPath("$.active", is(true)));

        mockMvc.perform(put("/customer/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated name")))
                .andExpect(jsonPath("$.email", is("updatedemail@server.com")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    public void updateReturnsBadRequestWhenNameIsNull() throws Exception {
        String json = """
                    {
                        "name": null,
                        "email": "email@server.com"
                    }
                """;

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Anna Smith")))
                .andExpect(jsonPath("$.email", is("anna.smith@server.com")))
                .andExpect(jsonPath("$.active", is(true)));

        mockMvc.perform(put("/customer/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "email",
            "email@",
            "server.com",
            "@server.com",
            "Name <user@example>"
    })
    public void updateReturnsBadRequestWhenEmailIsInvalid(String email) throws Exception {
        String json = String.format("""
                    {
                        "name": "Updated name",
                        "email": "%s"
                    }
                """, email);

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Anna Smith")))
                .andExpect(jsonPath("$.email", is("anna.smith@server.com")))
                .andExpect(jsonPath("$.active", is(true)));

        MvcResult result = mockMvc.perform(put("/customer/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String jsonResult = result.getResponse().getContentAsString();
        ExceptionResponse response = objectMapper.readValue(jsonResult, ExceptionResponse.class);
        assertThat(response.getType(), is("InvalidEmailException"));
    }

    @Test
    public void updateReturnsBadRequestWhenEmailIsNull() throws Exception {
        String json = """
                    {
                        "name": "Customer name",
                        "email": null
                    }
                """;

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Anna Smith")))
                .andExpect(jsonPath("$.email", is("anna.smith@server.com")))
                .andExpect(jsonPath("$.active", is(true)));

        MvcResult result = mockMvc.perform(put("/customer/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ExceptionResponse response = objectMapper.readValue(jsonResult, ExceptionResponse.class);
        assertThat(response.getType(), is("IllegalArgumentException"));
    }

    @Test
    void updateReturnsNotFoundWhenCustomerWasNotFound() throws Exception {
        String json = """
                    {
                        "name": "Updated name",
                        "email": "email@server.com"
                    }
                """;
        mockMvc.perform(put("/customer/999")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}