package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.DATE_TIME_DISPLAY_FORMATTER;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an Appointment in the address book.
 * Guarantees: immutable;
 */
public class Appointment {
    public static final String MESSAGE_CONSTRAINTS = "Appointment description must contain at least 1 alphabetic"
            + " character and has a limit of 80 characters.";
    public static final String VALIDATION_REGEX = "^(?!\\s*$)(?=.*[a-zA-Z]).{1,80}$";
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
        requireNonNull(description);
        requireNonNull(start);
        requireNonNull(end);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
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

    /**
     * Returns true if a given string is a valid appointment description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, start, end);
    }

    @Override
    public String toString() {
        return description
                + " FROM "
                + start.format(DATE_TIME_DISPLAY_FORMATTER)
                + " TO "
                + end.format(DATE_TIME_DISPLAY_FORMATTER);
    }

}
