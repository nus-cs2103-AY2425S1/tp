package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

public class End {

    private final String end;
    public End(String end) {
        requireNonNull(end);
        // checkArgument
        this.end = end;
    }

    @Override
    public String toString() {
        return this.end.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof End)) {
            return false;
        }

        End otherEnd = (End) other;
        return this.end.equals(otherEnd.end);
    }

    @Override
    public int hashCode() {
        return this.end.hashCode();
    }

}
