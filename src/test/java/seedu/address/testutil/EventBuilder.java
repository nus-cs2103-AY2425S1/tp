package seedu.address.testutil;

import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.name.Name;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Amy Bee Wedding";
    public static final String DEFAULT_DATE = "2021-10-10";

    private Name name;
    private Date date;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        date = eventToCopy.getDate();
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Event build() {
        return new Event(name, date);
    }

}
