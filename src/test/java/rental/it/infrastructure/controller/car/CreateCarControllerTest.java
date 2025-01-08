package rental.it.infrastructure.controller.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import rental.infrastructure.controller.car.CarResponse;
import rental.infrastructure.controller.car.CreateCarResponse;
import rental.it.infrastructure.controller.ControllerTestBase;
import rental.model.car.CarId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateCarControllerTest extends ControllerTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCar() throws Exception {
        String json = "{\"make\": \"New car make\", \"model\": \"New car model\", \"year\": 2025, \"dailyPrice\": 500.00}";

        MvcResult result = mockMvc.perform(post("/car")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        CreateCarResponse createCarResponse = objectMapper.readValue(jsonResponse, CreateCarResponse.class);

        mockMvc.perform(get("/car/"+createCarResponse.id())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createCarResponse.id().intValue())))
                .andExpect(jsonPath("$.make", is("New car make")))
                .andExpect(jsonPath("$.model", is("New car model")))
                .andExpect(jsonPath("$.year", is(2025)))
                .andExpect(jsonPath("$.dailyPrice", is(500.00)));
    }
}