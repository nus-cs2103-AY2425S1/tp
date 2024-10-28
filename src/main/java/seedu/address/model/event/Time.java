package seedu.address.model.event;

import java.time.LocalDateTime;

import seedu.address.model.person.Name;

/**
 * Represents an Event time in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Time {
    private final String eventTime;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    /**
     * Constructs a {@code Time}.
     *
     * @param eventTime A valid time.
     */
    public Time(String eventTime) {
        this.eventTime = eventTime;
        this.startTime = LocalDateTime.parse("2007-12-03T10:15:30");
        this.endTime = LocalDateTime.parse("2007-12-03T10:16:30");
    }

    public String getTime() {
        return eventTime;
    }

    public boolean isOverlap(Time other) {
        if (this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return eventTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Time otherTime = (Time) other;
        return eventTime.equals(otherTime.eventTime);
    }

    @Override
    public int hashCode() {
        return eventTime.hashCode();
    }
}
