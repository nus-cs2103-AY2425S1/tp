package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a log entry with a timestamp and a log message.
 */
public class Log {
    public static final String MESSAGE_CONSTRAINTS =
            "Timestamp must be in format of DD-MM-YYYY HH:MM and log messages should not be blank";

    /** Formatter for displaying and parsing appointment date and time. */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private final String logString;
    private final LocalDateTime timestamp;

    /**
     * Constructs a {@code Log} with the specified log message and timestamp.
     *
     * @param logString The log message.
     * @param timestamp The timestamp of the log entry.
     */
    public Log(String logString, LocalDateTime timestamp) {
        this.logString = logString;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a {@code Log} from a log entry string.
     * The log entry string should contain a timestamp and a log message.
     *
     * @param logEntry The log entry string.
     */
    public Log(String logEntry) {
        String[] logEntryParts = logEntry.split(" ");
        this.timestamp = LocalDateTime.parse(logEntryParts[0] + " " + logEntryParts[1],
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.logString = Arrays.stream(logEntryParts, 2, logEntryParts.length)
                .collect(Collectors.joining(" "));
    }

    /**
     * Checks if a given string is a valid log entry.
     * A valid log entry should contain a valid timestamp and a non-blank log message.
     *
     * @param test The string to test.
     * @return True if the string is a valid log entry, false otherwise.
     */
    public static boolean isValidLog(String test) {
        String[] logEntryParts = test.split(" ", 3);
        if (logEntryParts.length != 3) {
            return false;
        }

        String timestamp = logEntryParts[0] + " " + logEntryParts[1];

        try {
            LocalDateTime.parse(timestamp, FORMATTER);
        } catch (Exception e) {
            return false;
        }

        String logMessage = logEntryParts[2];
        return !logMessage.isBlank();
    }

    /**
     * Returns the log message.
     *
     * @return The log message.
     */
    public String getLogString() {
        return logString;
    }

    /**
     * Returns the timestamp of the log entry.
     *
     * @return The timestamp of the log entry.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the string representation of the log entry.
     *
     * @return The string representation of the log entry.
     */
    @Override
    public String toString() {
        return timestamp.format(DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")) + " " + logString;
    }

    /**
     * Checks if this log entry is equal to another object.
     *
     * @param other The other object to compare to.
     * @return True if the other object is equal to this log entry.
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
        return otherLog.getLogString().equals(getLogString())
                && otherLog.getTimestamp().equals(getTimestamp());
    }

    /**
     * Returns the hash code of the log entry.
     *
     * @return The hash code of the log entry.
     */
    @Override
    public int hashCode() {
        return logString.hashCode() + timestamp.hashCode();
    }
}
