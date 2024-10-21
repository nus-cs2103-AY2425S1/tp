package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_EVENT_NAME = "CS2103T Lecture";
    public static final String DEFAULT_EVENT_DESCRIPTION = "Software Engineering Lecture";
    public static final String DEFAULT_EVENT_START_DATE = "2024-10-01";
    public static final String DEFAULT_EVENT_END_DATE = "2024-10-10";

    private EventName eventName;
    private EventDescription eventDescription;
    private EventDuration eventDuration;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private int eventId;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        eventName = new EventName(DEFAULT_EVENT_NAME);
        eventDescription = new EventDescription(DEFAULT_EVENT_DESCRIPTION);
        eventStartDate = LocalDate.parse(DEFAULT_EVENT_START_DATE);
        eventEndDate = LocalDate.parse(DEFAULT_EVENT_END_DATE);
        eventDuration = new EventDuration(eventStartDate, eventEndDate);
        eventId = 0;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getEventName();
        eventDescription = eventToCopy.getEventDescription();
        eventStartDate = eventToCopy.getEventStartDate();
        eventEndDate = eventToCopy.getEventEndDate();
        eventDuration = new EventDuration(eventStartDate, eventEndDate);
        eventId = eventToCopy.getEventId();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Sets the {@code EventDescription} of the {@code Event} that we are building.
     */
    public EventBuilder withEventDescription(String eventDescription) {
        this.eventDescription = new EventDescription(eventDescription);
        return this;
    }

    /**
     * Sets the {@code EventDuration} of the {@code Event} that we are building.
     */
    public EventBuilder withEventDuration(String eventStartDate, String eventEndDate) {
        this.eventStartDate = LocalDate.parse(eventStartDate);
        this.eventEndDate = LocalDate.parse(eventEndDate);
        this.eventDuration = new EventDuration(this.eventStartDate, this.eventEndDate);
        return this;
    }

    public Event build() {
        return new Event(eventName, eventDescription, eventDuration, eventId);
    }
}
