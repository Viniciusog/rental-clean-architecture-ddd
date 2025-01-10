package rental.it.infrastructure.controller.customer;

import org.junit.jupiter.api.Test;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetCustomersByFilterControllerTest extends ControllerTestBase {

    @Test
    public void getCustomersByNameStartingSuccessfully() throws Exception {
        String nameStarting = "John";

        mockMvc.perform(get("/customer")
                .param("nameStarting", nameStarting))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {"id":2,"name":"John Smith","email":"john.smith@server.com","active":true},
                            {"id":3,"name":"John Lennon","email":"john.lennon@server.com","active":true}
                        ]
                        """));
    }

    @Test
    public void getCustomersByNameContainingSuccessfully() throws Exception {
        String nameContaining = "Smith";

        mockMvc.perform(get("/customer")
                        .param("nameContaining", nameContaining))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {"id":1,"name":"Anna Smith","email":"anna.smith@server.com","active":true},
                            {"id":2,"name":"John Smith","email":"john.smith@server.com","active":true}
                        ]
                        """));
    }

    @Test
    public void getCustomersByNameStartingAndContainingSuccessfully() throws Exception {
        String nameStarting = "John";
        String nameContaining = "Smith";

        mockMvc.perform(get("/customer")
                        .param("nameStarting", nameStarting)
                        .param("nameContaining", nameContaining))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {"id":2,"name":"John Smith","email":"john.smith@server.com","active":true}
                        ]
                        """));
    }

    @Test
    public void mustGetAllCustomersWhenNameStartingAndContainingAreNotInformed() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {"id":1,"name":"Anna Smith","email":"anna.smith@server.com","active":true},
                            {"id":2,"name":"John Smith","email":"john.smith@server.com","active":true},
                            {"id":3,"name":"John Lennon","email":"john.lennon@server.com","active":true},
                            {"id":4,"name":"Bill Gates","email":"bill.gates@server.com","active":false}
                        ]
                        """));
    }
}
