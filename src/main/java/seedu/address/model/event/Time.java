package seedu.address.model.event;

import seedu.address.model.person.Name;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents an Event time in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Time {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    public static final String MESSAGE_CONSTRAINTS =
            "Please express the time field of your event in the following format "
        + "\"t/from: YYYY-MM-DD HH:mm, to: YYYY-MM-DD HH:mm\"";

    /**
     * Constructs a {@code Time}.
     *
     * @param startTime A valid start time.
     * @param endTime A valid end time
     */
    public Time(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "From: " + startTime.format(formatter) + " "
                + "To: "+ endTime.format(formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "From: " + startTime.format(formatter) + " "
                + "To: "+ endTime.format(formatter);    }

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
