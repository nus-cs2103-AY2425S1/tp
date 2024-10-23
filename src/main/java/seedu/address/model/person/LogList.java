package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LogList {
    private final List<Log> logs;

    public LogList() {
        logs = new ArrayList<>();
    }

    public void addLog(Log log) {
        logs.add(log);
    }

    public List<Log> getLogs() {
        return Collections.unmodifiableList(logs);
    }

    @Override
    public String toString() {
        return logs.stream()
                .map(Log::toString)
                .collect(Collectors.joining("\n"));
    }

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

    @Override
    public int hashCode() {
        return logs.hashCode();
    }
}
