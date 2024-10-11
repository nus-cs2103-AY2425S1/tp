package seedu.address.model.person;

import java.util.Objects;

public class Log {
    private final String entry;

    /**
     * Constructs a {@code Log}.
     *
     * @param entry A valid log entry.
     */
    public Log(String entry) {
        this.entry = entry;
    }

    /**
     * Returns the log entry.
     */
    public String getEntry() {
        return entry;
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
        return entry.equals(otherLog.entry);
    }

    /**
     * Returns the hashcode of the log entry.
     */
    @Override
    public int hashCode() {
        return Objects.hash(entry);
    }

    /**
     * Returns the log entry as a string.
     */
    @Override
    public String toString() {
        return entry;
    }
}
