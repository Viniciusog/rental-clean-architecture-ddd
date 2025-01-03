package rental.ut.model.Rental;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import rental.model.rental.DateTimeRange;

import java.time.Instant;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static rental.fixture.RentalFixture.*;

public class DateTimeRangeTest {

    @Test
    void createSuccessfully() {
        Instant start = RENTAL_START_INSTANT;
        Instant end = RENTAL_END_INSTANT;

        DateTimeRange range = DateTimeRange.of(start, end);

        assertThat(range.start(), is(start));
        assertThat(range.end(), is(end));
    }

    @Test
    void throwsExceptionWhenStartTimeIsNull() {
        Instant start = null;
        Instant end = RENTAL_END_INSTANT;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DateTimeRange.of(start, end);
        });

        assertThat(exception.getMessage(), is("Start time is required."));
    }

    @Test
    void throwsExceptionWhenEndTimeIsNull() {
        Instant start = RENTAL_START_INSTANT;
        Instant end = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            DateTimeRange.of(start, end);
        });

        assertThat(exception.getMessage(), is("End time is required."));
    }

    @Test
    void throwsExceptionWhenStartTimeComesAfterEndTime() {
        Instant start = RENTAL_END_INSTANT;
        Instant end = RENTAL_START_INSTANT;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
           DateTimeRange.of(start, end);
        });

        assertThat(exception.getMessage(), is("Start time must come before end time."));
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.simple().forClass(DateTimeRange.class).verify();
    }
}