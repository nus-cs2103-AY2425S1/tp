package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogsList {
    private final List<Log> logs;

    public LogsList() {
        this.logs = new ArrayList<>();
    }

    public void addLog(Log log) {
        logs.add(log);
    }

    public List<Log> getLogs() {
        return new ArrayList<>(logs);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(logs);
    }

    @Override
    public String toString() {
        return logs.toString();
    }
}