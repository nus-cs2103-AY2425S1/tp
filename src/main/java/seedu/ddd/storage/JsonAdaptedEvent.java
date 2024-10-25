package seedu.ddd.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String description;
    private final String date;
    private final int eventId;

    private final List<JsonAdaptedContactId> clientIds;
    private final List<JsonAdaptedContactId> vendorIds;

    /**
     * Constructs a {@code JsonAdapted} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("date") String date,
        @JsonProperty("clientIds") List<JsonAdaptedContactId> clientIds,
        @JsonProperty("vendorIds") List<JsonAdaptedContactId> vendorIds,
        @JsonProperty("eventId") int eventId
    ) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.clientIds = new ArrayList<>(clientIds);
        this.vendorIds = new ArrayList<>(vendorIds);
        this.eventId = eventId;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().fullName;
        description = source.getDescription().description;
        date = source.getDate().toString();

        clientIds = new ArrayList<>();
        clientIds.addAll(source.getClientIds().stream()
                .map(JsonAdaptedContactId::new)
                .toList());
        vendorIds = new ArrayList<>();
        vendorIds.addAll(source.getVendorIds().stream()
                .map(JsonAdaptedContactId::new)
                .toList());
        eventId = source.getEventId().eventId;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Description.isValidDescription(date)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (!EventId.isValidEventId(eventId)) {
            throw new IllegalValueException(EventId.MESSAGE_CONSTRAINTS);
        }
        final EventId modelEventId = new EventId(eventId);

        return new Event(modelName, modelDescription, modelDate, modelEventId);
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
