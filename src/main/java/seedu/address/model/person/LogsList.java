package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a list of logs in the address book.
 */
public class LogsList {
    private final List<Log> logs;

    /**
     * Constructs a {@code LogsList}.
     */
    public LogsList() {
        this.logs = new ArrayList<>();
    }

    /**
     * Adds a log to the list.
     */
    public void addLog(Log log) {
        logs.add(log);
    }

    /**
     * Returns the list of logs.
     */
    public List<Log> getLogs() {
        return new ArrayList<>(logs);
    }

    /**
     * Returns true if both logs lists have the same logs.
     * This defines a weaker notion of equality between two logs lists.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LogsList otherLogsList)) {
            return false;
        }

        return logs.equals(otherLogsList.logs);
    }

    /**
     * Returns the hashcode of the logs list.
     */
    @Override
    public int hashCode() {
        return Objects.hash(logs);
    }

    /**
     * Returns the logs list as a string.
     */
    @Override
    public String toString() {
        return logs.toString();
    }
}
