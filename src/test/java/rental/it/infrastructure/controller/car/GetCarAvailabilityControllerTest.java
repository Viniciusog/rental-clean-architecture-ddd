package rental.it.infrastructure.controller.car;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

public class GetCarAvailabilityControllerTest extends ControllerTestBase {

    @Test
    void getFalseWhenTimeRangeIsInsideInterval() throws Exception {
        String json = "{ \"startTime\": \"2025-01-02T10:00:00Z\", \"endTime\": \"2025-01-05T10:00:00Z\" }";
        mockMvc.perform(get("/car/availability/1")
                        .param("startTime", "2025-01-02T10:00:00Z")
                        .param("endTime", "2025-01-05T10:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available", is(false)));

    }
}