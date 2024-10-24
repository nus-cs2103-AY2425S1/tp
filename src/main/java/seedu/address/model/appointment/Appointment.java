package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import seedu.address.model.person.Name;

/**
 * Represents an appointment with a person in SocialBook.
 * Guarantees: immutable; field values are validated
 */
public record Appointment(Name name, LocalDate date, LocalTime startTime, LocalTime endTime) {

    public Appointment withName(Name name) {
        return new Appointment(name, date, startTime, endTime);
    }

    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern(
                date.getYear() == LocalDate.now().getYear() ? "EEEE, MMMM d" : "EEEE, MMMM d, yyyy"));
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
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment otherAppointment)) {
            return false;
        }

        return name.equals(otherAppointment.name)
                && date.equals(otherAppointment.date)
                && startTime.equals(otherAppointment.startTime)
                && endTime.equals(otherAppointment.endTime);
    }

    @Override
    public String toString() {
        return String.format("%s, %s %s – %s",
                name, getFormattedDate(), getFormattedStartTime(), getFormattedEndTime());
    }
}
