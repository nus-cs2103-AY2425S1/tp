package seedu.address.model.event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Represents a time range with a start and (optional) end time.
 * Guarantees that the start time is always present.
 * The end time, if present, must be after the start time.
 */
public class TimeRange {

    private final LocalDateTime startDateTime;
    private final Optional<LocalDateTime> endDateTime;

    /**
     * Constructor for a TimeRange with both start and end times.
     * Ensures the end time is after the start time if provided.
     */
    public TimeRange(LocalDateTime startDateTime, Optional<LocalDateTime> endDateTime) {
        if (endDateTime.isPresent() && endDateTime.get().isBefore(startDateTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Constructor for a TimeRange with only a start time (no end time).
     */
    public TimeRange(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = Optional.empty();
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public Optional<LocalDateTime> getEndDateTime() {
        return endDateTime;
    }

    /**
     * Calculates the duration between the start and end times.
     * If the end time is not present, throws an exception.
     */
    public Duration getDuration() {
        if (endDateTime.isEmpty()) {
            throw new IllegalStateException("Cannot calculate duration without an end time");
        }
        return Duration.between(startDateTime, endDateTime.get());
    }

    /**
     * Checks if this time range has an end time.
     */
    public boolean hasEndTime() {
        return endDateTime.isPresent();
    }

    @Override
    public String toString() {
        return "Start: " + startDateTime + ", End: " + (endDateTime.isPresent() ? endDateTime.get() : "No end time");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof TimeRange)) {
            return false;
        }

        TimeRange otherTimeRange = (TimeRange) other;
        return startDateTime.equals(otherTimeRange.startDateTime)
                && endDateTime.equals(otherTimeRange.endDateTime);
    }

    @Override
    public int hashCode() {
        return startDateTime.hashCode() + endDateTime.hashCode();
    }
}

