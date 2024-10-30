package seedu.eventtory.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.eventtory.commons.exceptions.IllegalValueException;
import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.id.UniqueId;
import seedu.eventtory.model.vendor.Vendor;

/**
 * An Immutable EventTory that is serializable to JSON format.
 */
@JsonRootName(value = "eventTory")
public class JsonSerializableEventTory {

    public static final String MESSAGE_DUPLICATE_VENDOR = "Vendors list contains duplicate vendor(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";
    public static final String MESSAGE_NON_EXISTENT_VENDOR = "Association contains non-existent vendor ID";
    public static final String MESSAGE_NON_EXISTENT_EVENT = "Association contains non-existent event ID";
    public static final String MESSAGE_DUPLICATE_ASSOCIATION = "Duplicate association found in JSON.";

    private final List<JsonAdaptedVendor> vendors = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final List<JsonAdaptedAssociation> associations = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEventTory} with the given vendors, events and associations.
     */
    @JsonCreator
    public JsonSerializableEventTory(@JsonProperty("vendors") List<JsonAdaptedVendor> vendors,
                                       @JsonProperty("events") List<JsonAdaptedEvent> events,
                                       @JsonProperty("associations") List<JsonAdaptedAssociation> associations) {
        this.vendors.addAll(vendors);
        this.events.addAll(events);
        this.associations.addAll(associations);
    }

    /**
     * Converts a given {@code ReadOnlyEventTory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableEventTory(ReadOnlyEventTory source) {
        vendors.addAll(source.getVendorList().stream().map(JsonAdaptedVendor::new).toList());
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).toList());
        associations.addAll(source.getAssociationList().stream()
                .map(JsonAdaptedAssociation::new).toList());
    }

    /**
    * Converts this EventTory into the model's {@code EventTory} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public EventTory toModelType() throws IllegalValueException {
        EventTory eventTory = new EventTory();

        HashMap<UniqueId, Vendor> vendorMap = new HashMap<>();
        HashMap<UniqueId, Event> eventMap = new HashMap<>();

        for (JsonAdaptedVendor jsonAdaptedVendor : vendors) {
            Vendor vendor = jsonAdaptedVendor.toModelType();
            if (eventTory.hasVendor(vendor) || vendorMap.containsKey(vendor.getId())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VENDOR);
            }
            eventTory.addVendor(vendor);
            vendorMap.put(vendor.getId(), vendor);
        }

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            if (eventTory.hasEvent(event) || eventMap.containsKey(event.getId())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            eventTory.addEvent(event);
            eventMap.put(event.getId(), event);
        }

        for (JsonAdaptedAssociation jsonAdaptedAssociation : associations) {
            Association association = jsonAdaptedAssociation.toModelType();

            UniqueId vendorId = association.getVendorId();
            UniqueId eventId = association.getEventId();

            Vendor vendor = vendorMap.get(vendorId);
            Event event = eventMap.get(eventId);

            if (vendor == null) {
                throw new IllegalValueException(MESSAGE_NON_EXISTENT_VENDOR);
            }
            if (event == null) {
                throw new IllegalValueException(MESSAGE_NON_EXISTENT_EVENT);
            }

            if (eventTory.isVendorAssignedToEvent(vendor, event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSOCIATION);
            }

            eventTory.assignVendorToEvent(vendor, event);
        }

        return eventTory;
    }
}
