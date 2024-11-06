package seedu.address.model.appointment;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

/**
 * Represents an appointment with a person in the address book.
 * Guarantees: immutable; field values are validated
 */
public record Appointment(Name name, LocalDate date, LocalTime startTime, LocalTime endTime) {

    public static final String MESSAGE_INVALID_TIME_INTERVAL = "Appointment start time must be before end time";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a", Locale.US);

    public Appointment {
        checkArgument(isValidTimeInterval(startTime, endTime), MESSAGE_INVALID_TIME_INTERVAL);
    }

    public static boolean isValidTimeInterval(LocalTime startTime, LocalTime endTime) {
        return startTime.isBefore(endTime);
    }

    public Appointment withName(Name name) {
        return new Appointment(name, date, startTime, endTime);
    }

    public String getFormattedDate() {
        return date.format(DATE_FORMATTER);
    }

    public String getFormattedStartTime() {
        return startTime.format(TIME_FORMATTER);
    }

    public String getFormattedEndTime() {
        return endTime.format(TIME_FORMATTER);
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
        return new ToStringBuilder(this)
                .add("name", name)
                .add("date", date)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .toString();
    }
}
