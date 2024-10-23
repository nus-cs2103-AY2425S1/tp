package seedu.address.testutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Annual Charity Run";
    public static final String DEFAULT_DATE = "2024-12-15";
    public static final String DEFAULT_START_TIME = "08:00";
    public static final String DEFAULT_END_TIME = "10:00";
    public static final String DEFAULT_LOCATION = "East Coast Park";
    public static final String DEFAULT_DESCRIPTION = "Annual charity run for raising funds";

    private EventName eventName;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Location location;
    private Description description;
    private ObservableList<String> volunteers;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        eventName = new EventName(DEFAULT_EVENT_NAME);
        date = new Date(DEFAULT_DATE);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_END_TIME);
        location = new Location(DEFAULT_LOCATION);
        description = new Description(DEFAULT_DESCRIPTION);
        volunteers = FXCollections.observableArrayList();

    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getName();
        date = eventToCopy.getDate();
        startTime = eventToCopy.getStartTime();
        endTime = eventToCopy.getEndTime();
        location = eventToCopy.getLocation();
        description = eventToCopy.getDescription();
        volunteers = FXCollections.observableArrayList(eventToCopy.getVolunteers());
    }

    /**
     * Sets the {@code eventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String startTime) {
        this.startTime = new Time(startTime);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndTime(String endTime) {
        this.endTime = new Time(endTime);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Volunteers} of the {@code Event} that we are building.
     */
    public EventBuilder withVolunteers(String... volunteers) {
        this.volunteers = FXCollections.observableArrayList(volunteers);
        return this;
    }

    /**
     * Builds and returns the {@code Event} object.
     */
    public Event build() {
        return new Event(eventName, location, date, startTime, endTime, description);
    }
}
