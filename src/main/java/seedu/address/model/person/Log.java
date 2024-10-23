package seedu.address.model.person;

import java.time.LocalDateTime;

public class Log {
    private final String log;
    private final LocalDateTime timestamp;

    public Log(String log, LocalDateTime timestamp) {
        this.log = log;
        this.timestamp = timestamp;
    }

    public String getLog() {
        return log;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return log + " at " + timestamp;
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
        return otherLog.getLog().equals(getLog())
                && otherLog.getTimestamp().equals(getTimestamp());
    }

    @Override
    public int hashCode() {
        return log.hashCode() + timestamp.hashCode();
    }
}
