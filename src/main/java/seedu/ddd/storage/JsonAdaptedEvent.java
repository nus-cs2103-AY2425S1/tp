package seedu.ddd.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    private final int eventId;
    private final String description;
    private final List<JsonAdaptedClient> clients = new ArrayList<>();
    private final List<JsonAdaptedVendor> vendors = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdapted} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(
            @JsonProperty("description") String description,
            @JsonProperty("clients") List<JsonAdaptedClient> clients,
            @JsonProperty("vendors") List<JsonAdaptedVendor> vendors,
            @JsonProperty("eventId") int eventId
    ) {
        this.eventId = eventId;
        this.description = description;
        if (clients != null) {
            this.clients.addAll(clients);
        }
        if (vendors != null) {
            this.vendors.addAll(vendors);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        description = source.getDescription().description;
        eventId = source.getEventId().eventId;
        clients.addAll(source.getClients().stream()
                .map(JsonAdaptedClient::new)
                .collect(Collectors.toList()));
        vendors.addAll(source.getVendors().stream()
                .map(JsonAdaptedVendor::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final ArrayList<Client> modelClients = new ArrayList<>();
        if (clients.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Client.class.getSimpleName()));
        }
        for (JsonAdaptedClient client : clients) {
            modelClients.add((Client) client.toModelType());
        }

        final ArrayList<Vendor> modelVendors = new ArrayList<>();
        if (vendors.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Vendor.class.getSimpleName()));
        }
        for (JsonAdaptedVendor vendor : vendors) {
            modelVendors.add((Vendor) vendor.toModelType());
        }

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

        return new Event(modelClients, modelVendors, modelDescription, modelEventId);
    }
}
