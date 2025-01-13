package rental.it.infrastructure.controller.rental;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import rental.it.infrastructure.controller.ControllerTestBase;
import rental.model.car.CarId;
import rental.model.rental.DateTimeRange;
import rental.model.rental.RentalPriceCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateRentalControllerTest extends ControllerTestBase {

    @Autowired
    private RentalPriceCalculator rentalPriceCalculator;

    @Test
    public void mustUpdateSuccessfully() throws Exception {
        mockMvc.perform(get("/rental/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "customerId": 1,
                            "carId": 2,
                            "startTime": "2025-01-01T10:00:00Z",
                            "endTime": "2025-01-07T10:00:00Z",
                            "totalPrice": 2400.00
                        }
                        """));
        DateTimeRange newTimeRange = DateTimeRange.of(
                LocalDateTime.of(2025, 1, 2, 10, 0, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2025, 1, 5, 10, 0, 0).toInstant(ZoneOffset.UTC)
        );
        BigDecimal expectedNewTotalPrice = rentalPriceCalculator.execute(CarId.of(2L), newTimeRange);

        String json = """
                {
                     "startTime": "2025-01-02T10:00:00Z",
                     "endTime": "2025-01-05T10:00:00Z"
                }
                """;
        mockMvc.perform(put("/rental/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/rental/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("""
                        {
                            "id": 1,
                            "customerId": 1,
                            "carId": 2,
                            "startTime": "2025-01-02T10:00:00Z",
                            "endTime": "2025-01-05T10:00:00Z",
                            "totalPrice": %s
                        }
                        """, expectedNewTotalPrice.toString())));
    }

    @Test
    public void returnsConflictWhenCarIsNotAvailable() throws Exception {
        String json = """
                {
                     "startTime": "2025-01-05T10:00:00Z",
                     "endTime": "2025-01-07T10:00:00Z"
                }
                """;
        mockMvc.perform(put("/rental/2")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void returnsNotFoundWhenRentalWasNotFound() throws Exception {
        String json = """
                {
                     "startTime": "2025-01-05T10:00:00Z",
                     "endTime": "2025-01-07T10:00:00Z"
                }
                """;
        mockMvc.perform(put("/rental/999")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void returnsBadRequestWhenEndTimeIsBeforeStartTime() throws Exception {
        String json = """
                {
                     "startTime": "2025-01-07T10:00:00Z",
                     "endTime": "2025-01-05T10:00:00Z"
                }
                """;
        mockMvc.perform(put("/rental/2")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void returnsBadRequestWhenStartTimeIsNull() throws Exception {
        String json = """
                {
                     "startTime": null,
                     "endTime": "2025-01-07T10:00:00Z"
                }
                """;
        mockMvc.perform(put("/rental/2")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void returnsBadRequestWhenEndTimeIsNull() throws Exception {
        String json = """
                {
                     "startTime": "2025-01-05T10:00:00Z",
                     "endTime": null
                }
                """;
        mockMvc.perform(put("/rental/2")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}