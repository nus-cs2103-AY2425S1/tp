package seedu.address.model.person;

import java.util.Objects;

public class Log {
    private final String entry;

    public Log(String entry) {
        this.entry = entry;
    }

    public String getEntry() {
        return entry;
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
        return entry.equals(otherLog.entry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entry);
    }

    @Override
    public String toString() {
        return entry;
    }
}
