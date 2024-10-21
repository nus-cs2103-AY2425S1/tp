package seedu.address.testutil;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * A utility class to help with building {@link Event} objects.
 */
public class EventBuilder {
    public static final String DEFAULT_NAME = "IFG";

    private EventName name;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
    }

    /**
     * Initializes the {@code EventBuilder} with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
    }

    /**
     * Sets the {@link EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new EventName(name);
        return this;
    }

    public Event build() {
        return new Event(name);
    }
}
