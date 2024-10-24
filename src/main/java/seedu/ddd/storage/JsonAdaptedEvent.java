package seedu.ddd.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    private final int eventId;
    private final String description;
    private final List<JsonAdaptedContactId> clientIds = new ArrayList<>();
    private final List<JsonAdaptedContactId> vendorIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdapted} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(
            @JsonProperty("description") String description,
            @JsonProperty("clientIds") List<JsonAdaptedContactId> clientIds,
            @JsonProperty("vendorIds") List<JsonAdaptedContactId> vendorIds,
            @JsonProperty("eventId") int eventId
    ) {
        this.eventId = eventId;
        this.description = description;
        this.clientIds.addAll(clientIds);
        this.vendorIds.addAll(vendorIds);
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        description = source.getDescription().description;
        eventId = source.getEventId().eventId;
        clientIds.addAll(source.getClientIds().stream()
                .map(JsonAdaptedContactId::new)
                .toList());
        vendorIds.addAll(source.getVendorIds().stream()
                .map(JsonAdaptedContactId::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (!EventId.isValidEventId(eventId)) {
            throw new IllegalValueException(EventId.MESSAGE_CONSTRAINTS);
        }
        final EventId modelEventId = new EventId(eventId);

        return new Event(modelDescription, modelEventId);
    }

    /**
     * Retrieves the clientIds of the event
     * @throws IllegalValueException if there were any data constraints violated in the adapted contactId.
     */
    public List<ContactId> getClientIds() throws IllegalValueException {
        final List<ContactId> personClientIds = new ArrayList<>();
        for (JsonAdaptedContactId clientId : clientIds) {
            personClientIds.add(clientId.toModelType());
        }
        return personClientIds;
    }

    /**
     * Retrieves the clientIds of the event
     * @throws IllegalValueException if there were any data constraints violated in the adapted contactId.
     */
    public List<ContactId> getVendorIds() throws IllegalValueException {
        final List<ContactId> personVendorIds = new ArrayList<>();
        for (JsonAdaptedContactId vendorId : vendorIds) {
            personVendorIds.add(vendorId.toModelType());
        }
        return personVendorIds;
    }
}
