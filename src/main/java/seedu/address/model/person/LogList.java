package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a list of log entries.
 * Guarantees: logs are present and not null, field values are validated, immutable.
 */
public class LogList {
    private final List<Log> logs;

    /**
     * Constructs an empty {@code LogList}.
     */
    public LogList() {
        logs = new ArrayList<>();
    }

    /**
     * Constructs a {@code LogList} with the given log entries.
     *
     * @param logEntries The list of log entries to initialize the log list with.
     */
    public LogList(List<String> logEntries) {
        logs = new ArrayList<>();
        for (String logEntry : logEntries) {
            logs.add(new Log(logEntry));
        }
    }

    /**
     * Adds a log entry to the list.
     *
     * @param log The log entry to add.
     */
    public LogList addLog(Log log) {
        LogList newList = new LogList();
        newList.logs.addAll(logs);
        newList.logs.add(log);
        return newList;
    }

    /**
     * Returns an unmodifiable view of the log list.
     *
     * @return An unmodifiable list of log entries.
     */
    public List<Log> getLogs() {
        return Collections.unmodifiableList(logs);
    }

    /**
     * Returns the string representation of the log list.
     *
     * @return The string representation of the log list.
     */
    @Override
    public String toString() {
        return logs.stream()
                .map(Log::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Checks if this log list is equal to another object.
     *
     * @param other The other object to compare to.
     * @return True if the other object is equal to this log list.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LogList)) {
            return false;
        }

        LogList otherLogList = (LogList) other;
        return otherLogList.getLogs().equals(getLogs());
    }

    /**
     * Returns the hash code of the log list.
     *
     * @return The hash code of the log list.
     */
    @Override
    public int hashCode() {
        return logs.hashCode();
    }
}
