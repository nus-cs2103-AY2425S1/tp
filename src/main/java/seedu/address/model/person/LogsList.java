package seedu.address.model.person;

import seedu.address.model.log.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a list of logs in the address book.
 */
public class LogsList {
    private final List<Log> logs;

    /**
     * Constructs an empty LogList.
     */
    public LogsList() {
        this.logs = new ArrayList<>();
    }

    /**
     * Constructs a LogsList with the provided list of logs.
     *
     * @param logs The initial logs to populate the LogsList.
     */
    public LogsList(List<Log> logs) {
        this.logs = new ArrayList<>(logs);
    }


    /**
     * Adds a new log to the list.
     *
     * @param log The log to add.
     */
    public void addLog(Log log) {
        Objects.requireNonNull(log);
        logs.add(log);
    }

    /**
     * Returns an unmodifiable list of logs, preserving the order they were added.
     */
    public Log getDetailedLog(int index) {
        return logs.get(index);
    }

    /**
     * Returns the number of logs in the list.
     */
    public int size() {
        return logs.size();
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
