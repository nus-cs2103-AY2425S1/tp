package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents an Appointment in the address book.
 * Guarantees: immutable;
 */
public class Appointment {

    public final String description;
    public final LocalDateTime start;
    public final LocalDateTime end;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");

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

}
