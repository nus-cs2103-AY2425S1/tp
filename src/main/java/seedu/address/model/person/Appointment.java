package seedu.address.model.person;

import static java.util.Objects.isNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.ParserUtil.ENGLISH_FORMAT_WITH_TIME;
import static seedu.address.logic.parser.ParserUtil.TIME_FORMATTER;
import static seedu.address.logic.parser.ParserUtil.parseDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.address.model.person.exceptions.TimeParseException;

/**
 * Represents a Person's Appointment in the address book. An appointment is a 15-minute interval starting
 * from given date
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointment(String)}
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be in the calendar and in a format of \n"
            + "dd/MM/yyyy HH:mm or dd MM yyyy HH:mm or dd-MM-yyyy HH:mm";

    public static final LocalDate TODAY = LocalDate.now();

    // In ENGLISH_FORMAT_WITH_TIME format
    public final String dateTime;

    /**
     * Constructs a {@code Appointment}.
     *
     * @param date A valid Appointment date.
     */
    public Appointment(String date) {
        // Appointment constructor accepts dates of the formats defined in seedu.address.logic.parser.ParserUtil
        if (isNull(date) || date.equals("-")) {
            dateTime = "-";
        } else {
            checkArgument(isValidAppointment(date), MESSAGE_CONSTRAINTS);
            dateTime = parseDateTime(date).format(ENGLISH_FORMAT_WITH_TIME);
        }
    }

    /**
     * Returns true if a given string is a valid appointment date.
     */
    public static boolean isValidAppointment(String test) {
        if (test.equals("-")) {
            return true;
        }

        try {
            LocalDateTime temp = parseDateTime(test);
            return true;

        } catch (TimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if appointment is today.
     */
    public boolean isToday() {
        if (dateTime.equals("-")) {
            return false;
        }

        return LocalDateTime.parse(dateTime, ENGLISH_FORMAT_WITH_TIME)
                .toLocalDate().isEqual(TODAY);
    }

    /**
     * Returns true if appointment is on the specified date
     */
    public boolean isOn(LocalDate date) {
        if (dateTime.equals("-")) {
            return false;
        }
        return LocalDateTime.parse(dateTime, ENGLISH_FORMAT_WITH_TIME)
                .toLocalDate().isEqual(date);
    }

    /**
     * Returns true if appointment has passed.
     */
    public boolean hasPassed() {
        if (dateTime.equals("-")) {
            return false;
        }
        return LocalDateTime.parse(dateTime, ENGLISH_FORMAT_WITH_TIME)
                .toLocalDate().isBefore(TODAY);
    }

    /**
     * Returns true if appointment is in the future.
     */
    public boolean hasNotPassed() {
        if (dateTime.equals("-")) {
            return false;
        }
        return LocalDateTime.parse(dateTime, ENGLISH_FORMAT_WITH_TIME)
                .toLocalDate().isAfter(TODAY);
    }

    /**
     * Returns the end time of the appointment, which is 15 minutes after {@code dateTime}.
     */
    public String getEndTime() {
        if (dateTime.equals("-")) {
            return "-";
        } else {
            return LocalDateTime.parse(dateTime, ENGLISH_FORMAT_WITH_TIME)
                                .toLocalTime()
                                .plusMinutes(15)
                                .format(TIME_FORMATTER);
        }
    }

    /**
     * Checks if appointment overlaps with given appointment.
     */
    public boolean isWithinInterval(Appointment other) {
        if (dateTime.equals("-") || other.dateTime.equals("-")) {
            return false;
        }
        LocalDateTime time = LocalDateTime.parse(dateTime, ENGLISH_FORMAT_WITH_TIME);
        LocalDateTime otherTime = LocalDateTime.parse(other.dateTime, ENGLISH_FORMAT_WITH_TIME);
        return time.toLocalDate().isEqual(otherTime.toLocalDate())
                && (otherTime.isAfter(time.minusMinutes(15))
                && otherTime.isBefore(time.plusMinutes(15)));
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

        if (this.dateTime.equals("-") || otherAppointment.dateTime.equals("-")) {
            return this.dateTime.equals(otherAppointment.dateTime);
        }

        // checks if other start time is within the 15-minute interval
        LocalDateTime time = LocalDateTime.parse(dateTime, ENGLISH_FORMAT_WITH_TIME);
        LocalDateTime otherTime = LocalDateTime.parse(otherAppointment.dateTime, ENGLISH_FORMAT_WITH_TIME);

        return time.toLocalDate().isEqual(otherTime.toLocalDate())
                && time.plusMinutes(15).isAfter(otherTime) && time.isBefore(otherTime.plusMinutes(1));


    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return dateTime;
    }
}
