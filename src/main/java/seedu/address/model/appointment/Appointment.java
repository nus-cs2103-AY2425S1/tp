package seedu.address.model.appointment;

import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Appointment in the address book.
 * Guarantees: immutable;
 */
public class Appointment {

    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param description the description of the appointment.
     * @param start the start time in dd-MM-yyyy-HH-mm format.
     * @param end the end time in dd-MM-yyyy-HH-mm format.
     */
    public Appointment(String description, LocalDateTime start, LocalDateTime end) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppt = (Appointment) other;
        return description.equals(otherAppt.description)
                && start.equals(otherAppt.start)
                && end.equals(otherAppt.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, start, end);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("description", description)
                .add("start", start.format(DATE_TIME_FORMATTER))
                .add("end", end.format(DATE_TIME_FORMATTER))
                .toString();
    }

}
