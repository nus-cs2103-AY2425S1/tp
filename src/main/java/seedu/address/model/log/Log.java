package seedu.address.model.log;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a log in the address book.
 */
public class Log {

    public static final String MESSAGE_CONSTRAINTS = "Log entry can take any alphanumeric values, "
            + "and it should not be blank";
    public static final String VALIDATION_REGEX = ".+";

    private final String entry;
    private final AppointmentDate appointmentDate;

    /**
     * Constructs a {@code Log}.
     *
     * @param entry A valid log entry.
     * @param appointmentDate A valid appoinmentDate.
     */
    public Log(AppointmentDate appointmentDate, String entry) {
        requireNonNull(appointmentDate, entry);
        checkArgument(isValidEntry(entry), MESSAGE_CONSTRAINTS);
        this.entry = entry;
        this.appointmentDate = appointmentDate;
    }

    /**
     * Returns true if a given string is a valid entry.
     */
    public static boolean isValidEntry(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the log entry.
     */
    public String getEntry() {
        return entry;
    }
    /**
     * Returns the appointmentDate of the session.
     */
    public String getAppointmentDate() {
        return appointmentDate.toString();
    }

    /**
     * Returns detailed information about the log entry.
     */
    public String toDetailedString() {
        return String.format("Appointment Date: %s\nEntry: %s",
                getAppointmentDate(), getEntry());
    }

    /**
     * Returns true if both logs have the same entry.
     * This defines a weaker notion of equality between two logs.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Log)) {
            return false;
        }

        Log otherLog = (Log) other;
        return entry.equals(otherLog.entry) && appointmentDate.equals(otherLog.appointmentDate);
    }

    /**
     * Returns the hashcode of the log entry.
     */
    @Override
    public int hashCode() {
        return Objects.hash(entry, appointmentDate);
    }

    /**
     * Returns the log entry as a string with a limit of 100 char.
     */
    @Override
    public String toString() {
        String truncatedEntry = entry.length() > 100 ? entry.substring(0, 100) : entry;
        return new ToStringBuilder(this)
                .add("Appointment Date", appointmentDate.toString())
                .add("Entry", truncatedEntry)
                .toString();
    }
}
