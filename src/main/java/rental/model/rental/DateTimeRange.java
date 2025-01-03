package rental.model.rental;

import rental.Validation;

import java.time.Instant;

public class DateTimeRange {

    private Instant start;
    private Instant end;

    private DateTimeRange(Instant start, Instant end) {
        this.start = start;
        this.end = end;
    }

    public static DateTimeRange of(Instant start, Instant end) {
        validate(start, end);
        return new DateTimeRange(start, end);
    }

    public Instant start() {
        return start;
    }

    public Instant end() {
        return end;
    }

    private static void validate(Instant start, Instant end) {
        Validation.required(start, "Start time is required.");
        Validation.required(end, "End time is required.");

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must come before end time.");
        }
    }
}