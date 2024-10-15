package seedu.address.model.event;

import seedu.address.model.person.Name;

/**
 * Represents an Event time in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Time {
    private final String eventTime;

    /**
     * Constructs a {@code Time}.
     *
     * @param eventTime A valid time.
     */
    public Time(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getTime() {
        return eventTime;
    }

    @Override
    public String toString() {
        return eventTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Time otherTime = (Time) other;
        return eventTime.equals(otherTime.eventTime);
    }

    @Override
    public int hashCode() {
        return eventTime.hashCode();
    }
}
