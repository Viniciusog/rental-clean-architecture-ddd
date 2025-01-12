package rental.it.infrastructure.controller.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import rental.infrastructure.configuration.ExceptionResponse;
import rental.infrastructure.controller.rental.dto.CreateRentalResponse;
import rental.it.infrastructure.controller.ControllerTestBase;
import rental.model.car.CarId;
import rental.model.rental.DateTimeRange;
import rental.model.rental.RentalId;
import rental.model.rental.RentalPriceCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;

public class CreateRentalControllerTest extends ControllerTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RentalPriceCalculator rentalPriceCalculator;

    @Test
    void mustCreateSuccessfully() throws Exception {
        String json = """
                {
                    "customerId": 2,
                    "carId": 3,
                    "startTime": "2025-01-01T10:00:00Z",
                    "endTime": "2025-01-03T10:00:00Z"
                }
                """;
        DateTimeRange timeRange = DateTimeRange.of(
                LocalDateTime.of(2025, 1, 1, 10, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 1, 3, 10, 0, 0).toInstant(ZoneOffset.UTC)
        );
        BigDecimal expectedRentalTotalPrice = rentalPriceCalculator.execute(CarId.of(3L), timeRange);
        MvcResult result = mockMvc.perform(post("/rental")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        CreateRentalResponse response = objectMapper.readValue(jsonResult, CreateRentalResponse.class);
        RentalId rentalId = RentalId.of(response.id());

        mockMvc.perform(get("/rental/" + rentalId.value())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("""
                        {
                            "id": %d,
                            "customerId": 2,
                            "carId": 3,
                            "startTime": "2025-01-01T10:00:00Z",
                            "endTime": "2025-01-03T10:00:00Z",
                            "totalPrice": %s
                        }
                        """, rentalId.value(), expectedRentalTotalPrice.toString())));
    }

    @Test
    void mustReturnNotFoundWhenCarWasNotFound() throws Exception {
        String json = """
                {
                    "customerId": 2,
                    "carId": 999,
                    "startTime": "2025-01-01T10:00:00Z",
                    "endTime": "2025-01-03T10:00:00Z"
                }
                """;
        MvcResult result = mockMvc.perform(post("/rental")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ExceptionResponse exceptionResponse = objectMapper.readValue(jsonResult, ExceptionResponse.class);
        assertThat(exceptionResponse.getType(), is("CarNotFoundException"));
    }

    @Test
    void mustReturnNotFoundWhenCustomerWasNotFound() throws Exception {
        String json = """
                {
                    "customerId": 999,
                    "carId": 3,
                    "startTime": "2025-01-01T10:00:00Z",
                    "endTime": "2025-01-03T10:00:00Z"
                }
                """;
        MvcResult result = mockMvc.perform(post("/rental")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ExceptionResponse exceptionResponse = objectMapper.readValue(jsonResult, ExceptionResponse.class);
        assertThat(exceptionResponse.getType(), is("CustomerNotFoundException"));
    }

    @Test
    void mustReturnBadRequestWhenEndTimeIsBeforeStartTime() throws Exception {
        String json = """
                {
                    "customerId": 999,
                    "carId": 3,
                    "startTime": "2025-01-03T10:00:00Z",
                    "endTime": "2025-01-01T10:00:00Z"
                }
                """;
        MvcResult result = mockMvc.perform(post("/rental")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        ExceptionResponse exceptionResponse = objectMapper.readValue(jsonResult, ExceptionResponse.class);
        assertThat(exceptionResponse.getType(), is("IllegalArgumentException"));
        assertThat(exceptionResponse.getMessage(), is("Start time must come before end time."));
    }
}