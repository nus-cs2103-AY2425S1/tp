package seedu.address.model.person;

import java.time.LocalDateTime;

public class Log {
    private final String logString;
    private final LocalDateTime timestamp;

    public Log(String logString, LocalDateTime timestamp) {
        this.logString = logString;
        this.timestamp = timestamp;
    }

    public String getLogString() {
        return logString;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + logString;
    }

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

    @Override
    public int hashCode() {
        return logString.hashCode() + timestamp.hashCode();
    }
}
