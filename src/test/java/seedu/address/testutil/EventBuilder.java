package seedu.address.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Event A";
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2023, 10, 1);

    private String name;
    private LocalDate date;
    private Set<Person> attendees;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = DEFAULT_NAME;
        date = DEFAULT_DATE;
        attendees = new HashSet<>();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getEventName();
        date = eventToCopy.getDate();
        attendees = new HashSet<>(eventToCopy.getAttendees());
    }

    /**
     * Sets the {@code name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code attendees} of the {@code Event} that we are building.
     */
    public EventBuilder withAttendees(Set<Person> attendees) {
        this.attendees = attendees;
        return this;
    }

    public Event build() {
        return new Event(name, date, attendees);
    }
}
