package seedu.address.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Event A";
    public static final LocalDate DEFAULT_DATE = LocalDate.of(2023, 10, 1);
    public static final Address DEFAULT_LOCATION = new Address("123, Kent Ridge Street");
    public static final Set<Person> DEFAULT_ATTENDEES = SampleDataUtil.getSampleAttendees();
    public static final Set<Index> DEFAULT_INDEXES = SampleDataUtil.getSampleIndexes();

    private String name;
    private LocalDate date;
    private Address location;
    private Set<Person> attendees;
    private Set<Index> indexes;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = DEFAULT_NAME;
        date = DEFAULT_DATE;
        location = DEFAULT_LOCATION;
        attendees = DEFAULT_ATTENDEES;
        this.indexes = DEFAULT_INDEXES;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getEventName();
        date = eventToCopy.getDate();
        location = eventToCopy.getLocation();
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
     * Sets the {@code location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(Address location) {
        this.location = location;
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
        return new Event(name, date, location, attendees);
    }

    public Event buildWithNoAttendees() {
        return new Event(name, date, location, new HashSet<>());
    }
}
