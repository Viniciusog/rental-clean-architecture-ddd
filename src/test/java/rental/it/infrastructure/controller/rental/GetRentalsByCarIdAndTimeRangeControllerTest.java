package rental.it.infrastructure.controller.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import rental.infrastructure.configuration.ExceptionResponse;
import rental.it.infrastructure.controller.ControllerTestBase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;

public class GetRentalsByCarIdAndTimeRangeControllerTest extends ControllerTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void mustGetByCarIdAndTimeRange() throws Exception {
        mockMvc.perform(get("/rental/search")
                        .param("carId", "3")
                        .param("startTime", "2025-01-20T10:00:00Z")
                        .param("endTime", "2025-02-18T10:00:00Z"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "id": 5,
                                "customerId": 3,
                                "carId": 3,
                                "startTime": "2025-02-01T10:00:00Z",
                                "endTime": "2025-02-10T10:00:00Z",
                                "totalPrice": 1000.00
                            },
                            {
                                "id": 6,
                                "customerId": 2,
                                "carId": 3,
                                "startTime": "2025-02-15T10:00:00Z",
                                "endTime": "2025-02-20T10:00:00Z",
                                "totalPrice": 500.00
                            }
                        ]
                        """));
    }

    @Test
    public void mustReturnBadRequestWhenEndTimeIsBeforeStartTime() throws Exception {
        MvcResult result = mockMvc.perform(get("/rental/search")
                        .param("carId", "3")
                        .param("startTime", "2025-02-18T10:00:00Z")
                        .param("endTime", "2025-01-20T10:00:00Z"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ExceptionResponse response = objectMapper.readValue(jsonResult, ExceptionResponse.class);
        assertThat(response.getType(), is("IllegalArgumentException"));
        assertThat(response.getMessage(), is("Start time must come before end time."));
    }
}
