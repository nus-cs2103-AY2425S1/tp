package seedu.address.model.log;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a log in the address book.
 */
public class Log {

    public static final String MESSAGE_CONSTRAINTS = "A Log requires a date in the format of dd MMM yyyy,"
            + "and a log description that can take any characters, including symbols, numbers, etc.\n"
            + "Both date and description should not be blank.\n"
            + "Format: d/date l/log description\n"
            + "e.g. d/20 May 2024 l/First appointment with John. John shared 3 problems during the session.";
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
    public AppointmentDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Returns the String-formatted version of appointmentDate.
     */
    public String getAppointmentDateString() {
        return appointmentDate.toString();
    }

    /**
     * Return to string in the format "date|description" for JsonStorage
     */
    public String toStorageString() {
        return appointmentDate.toString() + "|" + entry;
    }

    /**
     * Converts a storage string into a {@code Log} object.
     *
     * @param storageString the string to convert, formatted as "datePart|descriptionPart"
     * @return a {@code Log} object
     * @throws IllegalArgumentException if the format is invalid
     */
    public static Log fromStorageString(String storageString) {
        String[] parts = storageString.split("\\|");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid log format: " + storageString);
        }

        String datePart = parts[0].trim();
        String descriptionPart = parts[1].trim();

        AppointmentDate appointmentDate = new AppointmentDate(datePart);
        return new Log(appointmentDate, descriptionPart);
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
        String truncatedEntry = entry.length() > 100
                ? entry.substring(0, 100) + "..."
                : entry;
        return String.format("Log{Appointment Date=%s, Entry=%s}", appointmentDate.toString(), truncatedEntry);
    }
}
