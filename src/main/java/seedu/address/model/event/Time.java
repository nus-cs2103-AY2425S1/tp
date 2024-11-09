package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents an Event time in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Start time should be before end time";
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");

    /**
     * Constructs a {@code Time}.
     *
     * @param startTime A valid start time.
     * @param endTime A valid end time
     */
    public Time(LocalDateTime startTime, LocalDateTime endTime) {
        requireAllNonNull(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static boolean isValidTime(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.isBefore(endTime);
    }

    public boolean isValidTime() {
        return startTime.isBefore(endTime);
    }

    public String getStartTime() {
        return this.startTime.format(formatter);
    }

    public LocalDateTime getLocalDateStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime.format(formatter);
    }

    public String getTime() {
        return "From: " + startTime.format(formatter) + " "
                + "To: " + endTime.format(formatter);
    }

    /**
     * Returns true if a given {@code Time} overlaps with this {@code Time}.
     */
    public boolean isOverlap(Time other) {
        return !this.endTime.isBefore(other.startTime)
                && !this.startTime.isAfter(other.endTime)
                && !this.endTime.isEqual(other.startTime)
                && !this.startTime.isEqual(other.endTime);
    }

    @Override
    public String toString() {
        return "From: " + startTime.format(formatter) + " "
                + "To: " + endTime.format(formatter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return startTime.equals(otherTime.startTime)
                && endTime.equals(otherTime.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}
