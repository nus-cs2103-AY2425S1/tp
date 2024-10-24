package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * Represents an appointment with a Person in the address book.
 * Guarantees: immutable; field values are validated
 */
public record Appointment(LocalDate date, LocalTime startTime, LocalTime endTime) {

    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern(
                date.getYear() == LocalDate.now().getYear() ? "EEEE, MMMM d" : "EEEE, MMMM d, yyyy"));
    }

    public LocalDate getUnformattedDate() {
        return date;
    }

    public String getFormattedStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern(isSamePeriod() ? "h:mm" : "h:mm a"));
    }

    public String getFormattedEndTime() {
        return endTime.format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    public boolean isSamePeriod() {
        return startTime.get(ChronoField.AMPM_OF_DAY) == endTime.get(ChronoField.AMPM_OF_DAY);
    }

    /**
     * Returns true if this appointment conflicts with the given appointment.
     */
    public boolean hasConflictWith(Appointment otherAppointment) {
        return otherAppointment != null
                && date.equals(otherAppointment.date)
                && startTime.isBefore(otherAppointment.endTime)
                && endTime.isAfter(otherAppointment.startTime);
    }

    @Override
    public String toString() {
        return "%s %s – %s".formatted(getFormattedDate(), getFormattedStartTime(), getFormattedEndTime());
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
        return date.equals(otherAppointment.date)
                && startTime.equals(otherAppointment.startTime)
                && endTime.equals(otherAppointment.endTime);
    }
}
