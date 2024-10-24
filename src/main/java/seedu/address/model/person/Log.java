package seedu.address.model.person;

import java.time.LocalDateTime;

/**
 * Represents a log entry with a timestamp and a log message.
 */
public class Log {
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
        return "[" + timestamp + "] " + logString;
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
