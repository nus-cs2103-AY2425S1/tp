package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String eventName;
    private final String eventDescription;
    private final String eventFrom;
    private final String eventTo;
    private final int eventId;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventName") String eventName,
                            @JsonProperty("eventDescription") String eventDescription,
                            @JsonProperty("eventFrom") String eventFrom,
                            @JsonProperty("eventTo") String eventTo,
                            @JsonProperty("id") int eventId) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventFrom = eventFrom;
        this.eventTo = eventTo;
        this.eventId = eventId;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event event) {
        eventName = event.getEventName().toString();
        eventDescription = event.getEventDescription().toString();
        eventFrom = event.getEventStartDate().toString();
        eventTo = event.getEventEndDate().toString();
        eventId = event.getEventId();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (eventName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName())
            );
        }
        if (!EventName.isValidName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        final EventName modelEventName = new EventName(eventName);

        if (eventDescription == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDescription.class.getSimpleName())
            );
        }
        final EventDescription modelEventDescription = new EventDescription(eventDescription);

        if (eventFrom == null || eventTo == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDuration.class.getSimpleName())
            );
        }
        final EventDuration modelEventDuration = getEventDuration();

        return new Event(modelEventName, modelEventDescription, modelEventDuration, eventId);
    }

    private EventDuration getEventDuration() throws IllegalValueException {
        final EventDuration modelEventDuration;
        try {
            final LocalDate modelStartDate = LocalDate.parse(eventFrom);
            final LocalDate modelEndDate = LocalDate.parse(eventTo);
            if (!EventDuration.isValidDuration(modelStartDate, modelEndDate)) {
                throw new IllegalValueException(EventDuration.MESSAGE_CONSTRAINTS_DATE_ORDER);
            }
            modelEventDuration = new EventDuration(modelStartDate, modelEndDate);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(EventDuration.MESSAGE_CONSTRAINTS_DATE_STRING);
        }
        return modelEventDuration;
    }

}
