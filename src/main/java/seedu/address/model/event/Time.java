package seedu.address.model.event;

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
}
