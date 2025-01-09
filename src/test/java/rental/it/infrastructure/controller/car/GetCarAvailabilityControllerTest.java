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
    void availableIsFalseWhenTimeRangeIsInsideInterval() throws Exception {
        mockMvc.perform(get("/car/availability/1")
                        .param("startTime", "2025-01-02T10:00:00Z")
                        .param("endTime", "2025-01-05T10:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available", is(false)));

    }

    @Test
    void availableIsTrueWhenTimeRangeIsBeforeInterval() throws Exception {
        mockMvc.perform(get("/car/availability/1")
                        .param("startTime", "2025-01-01T10:00:00Z")
                        .param("endTime", "2025-01-04T09:59:59Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available", is(true)));

    }

    @Test
    void availableIsFalseWhenTimeRangeIsTheInterval() throws Exception {
        mockMvc.perform(get("/car/availability/1")
                        .param("startTime", "2025-01-04T10:00:00Z")
                        .param("endTime", "2025-01-10T10:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available", is(false)));

    }

    @Test
    void availableIsTrueWhenTimeRangeIsAfterInterval() throws Exception {
        mockMvc.perform(get("/car/availability/1")
                        .param("startTime", "2025-01-10T10:00:01Z")
                        .param("endTime", "2025-01-15T10:00:00Z")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available", is(true)));

    }

    @Test
    void throwsExceptionWhenCarWasNotFound() throws Exception {
        mockMvc.perform(get("/car/availability/999")
                        .param("startTime", "2025-01-10T10:00:01Z")
                        .param("endTime", "2025-01-15T10:00:00Z"))
                .andExpect(status().isNotFound());
    }

    @Test
    void throwsExceptionWhenStartTimeIsAfterEndTime() throws Exception {
        mockMvc.perform(get("/car/availability/1")
                        .param("startTime", "2025-01-15T10:00:00Z")
                        .param("endTime", "2025-01-10T10:00:01Z"))
                .andExpect(status().isBadRequest());
    }
}