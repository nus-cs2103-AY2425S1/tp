package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an appointment in the address book.
 * An {@code Appointment} consists of a {@code Date}, start time ({@code From}), and end time ({@code To}).
 * All fields must be non-null.
 */
public class Appointment {

    public static final Appointment EMPTY_APPOINTMENT = new Appointment(Date.EMPTY_DATE, From.EMPTY_FROM,
            To.EMPTY_TO);
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

    public static boolean isValidPeriod(From from, To to) {
        return !from.value.isAfter(to.value);
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


    /**
     * Checks if the appointment is scheduled for today.
     *
     * @return true if the appointment date is today; false otherwise.
     */
    public boolean isToday() {
        return date.isToday();
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

        Appointment otherAppointment = (Appointment) other;

        boolean hasSameDate = date.equals(otherAppointment.date);
        boolean hasSameFrom = from.equals(otherAppointment.from);
        boolean hasSameTo = to.equals(otherAppointment.to);

        return hasSameDate && hasSameFrom && hasSameTo;
    }

    @Override
    public String toString() {
        if (this.equals(Appointment.EMPTY_APPOINTMENT)) {
            return "-";
        }
        return String.format("Date: %s (From: %s To: %s)", date, from, to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, from, to);
    }
}
