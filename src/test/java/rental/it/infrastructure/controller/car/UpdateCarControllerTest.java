package rental.it.infrastructure.controller.car;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateCarControllerTest extends ControllerTestBase {

    @Test
    public void mustUpdateSuccessfully() throws Exception {
        String json = "{ \"make\": \"Updated make\", \"model\": \"Updated model\", \"year\": 2025, \"dailyPrice\": 999.00}";

        mockMvc.perform(get("/car/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")))
                .andExpect(jsonPath("$.year", is(2024)))
                .andExpect(jsonPath("$.dailyPrice", is(120.00)));

        mockMvc.perform(put("/car/1")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/car/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Updated make")))
                .andExpect(jsonPath("$.model", is("Updated model")))
                .andExpect(jsonPath("$.year", is(2025)))
                .andExpect(jsonPath("$.dailyPrice", is(999.00)));
    }

    @Test
    void updateCarMustReturnNotFound() throws Exception {
        String json = "{ \"make\": \"Updated make\", \"model\": \"Updated model\", \"year\": 2025, \"dailyPrice\": 999.00}";

        mockMvc.perform(put("/car/999").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCarMustReturnBadRequestWhenMakeIsNull() throws Exception {
        String json = "{ \"make\": null, \"model\": \"Updated model\", \"year\": 2025, \"dailyPrice\": 999.00}";

        mockMvc.perform(put("/car/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCarMustReturnBadRequestWhenModelIsNull() throws Exception {
        String json = "{ \"make\": \"Updated make\", \"model\": null, \"year\": 2025, \"dailyPrice\": 999.00}";

        mockMvc.perform(put("/car/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCarMustReturnBadRequestWhenYearIsNull() throws Exception {
        String json = "{ \"make\": \"Updated make\", \"model\": \"Updated model\", \"year\": null, \"dailyPrice\": 999.00}";

        mockMvc.perform(put("/car/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
