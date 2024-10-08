package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

public class Start {

    private final String start;
    public Start(String start) {
        requireNonNull(start);
        // checkArgument
        this.start = start;
    }

    @Override
    public String toString() {
        return this.start.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Start)) {
            return false;
        }

        Start otherStart = (Start) other;
        return this.start.equals(otherStart.start);
    }

    @Override
    public int hashCode() {
        return this.start.hashCode();
    }
}
