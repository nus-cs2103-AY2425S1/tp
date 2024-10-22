package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an appointment in the address book.
 * An {@code Appointment} consists of a {@code Date}, start time ({@code From}), and end time ({@code To}).
 * All fields must be non-null.
 */
public class Appointment {
    private final Date date;
    private final From from;
    private final To to;

    /**
     * Constructs an {@code Appointment} with the specified date, start time, and end time.
     * All values must be non-null.
     *
     * @param date The date of the appointment.
     * @param from The start time of the appointment.
     * @param to   The end time of the appointment.
     */
    public Appointment(Date date, From from, To to) {
        requireAllNonNull(date, from, to);
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public From getFrom() {
        return from;
    }

    public To getTo() {
        return to;
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

        Appointment a = (Appointment) other;
        return date.equals(a.date)
                && from.equals(a.from)
                 && to.equals(a.to);
    }

    public boolean isEmpty() {
        return date.value.isEmpty() && from.value.isEmpty() && to.value.isEmpty();
    }
    @Override
    public String toString() {
        return String.format("Date: %s (From: %s To: %s)", date.value, from.value, to.value);
    }

    @Override
    public int hashCode() {
        return from.hashCode();
    }
}
