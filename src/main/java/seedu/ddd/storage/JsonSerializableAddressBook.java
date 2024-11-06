package seedu.ddd.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.ReadOnlyAddressBook;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Event;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_CLIENT = "Clients list contains duplicate client(s).";
    public static final String MESSAGE_DUPLICATE_VENDOR = "Vendors list contains duplicate vendor(s).";
    public static final String MESSAGE_NO_CLIENT = "Event does not have a valid client.";
    public static final String MESSAGE_NO_VENDOR = "Event does not have a valid vendor.";
    public static final String MESSAGE_CONTACT_NOT_CREATED = "Event contains contact(s) that have not been created.";
    public static final String MESSAGE_ASSOCIATION_MISMATCH = "Association between event and contact do not match.";
    private final List<JsonAdaptedContact> contacts = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private int nextContactId;
    private int nextEventId;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("contacts") List<JsonAdaptedContact> contacts,
        @JsonProperty("events") List<JsonAdaptedEvent> events,
        @JsonProperty("nextContactId") int nextContactId,
        @JsonProperty("nextEventId") int nextEventId) {
        this.contacts.addAll(contacts);
        this.events.addAll(events);
        this.nextContactId = nextContactId;
        this.nextEventId = nextEventId;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContactFactory::create)
                .collect(Collectors.toList()));
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new)
                .collect(Collectors.toList()));
        nextContactId = AddressBook.getNextContactId();
        nextEventId = AddressBook.getNextEventId();
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        Map<Integer, Set<Id>> eventIdsTable = new HashMap<>();
        int maxContactId = 0;
        int maxEventId = 0;
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact = jsonAdaptedContact.toModelType();
            if (addressBook.hasContact(contact)) {
                if (contact instanceof Client) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_CLIENT);
                } else if (contact instanceof Vendor) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_VENDOR);
                }
            }
            int contactId = contact.getId().id;
            eventIdsTable.put(contactId, jsonAdaptedContact.getEventIds());
            addressBook.addContact(contact);
            if (contactId > maxContactId) {
                maxContactId = contactId;
            }
        }
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();

            jsonAdaptedEvent.getClientIds().stream()
                    .map(addressBook::getContact)
                    .filter(contact -> contact instanceof Client)
                    .map(contact -> (Client) contact)
                    .forEach(event::addClient);

            boolean hasClients = jsonAdaptedEvent.getClientIds().stream().anyMatch(Objects::nonNull);
            if (!hasClients) {
                throw new IllegalValueException(MESSAGE_NO_CLIENT);
            }

            jsonAdaptedEvent.getVendorIds().stream()
                    .map(addressBook::getContact)
                    .filter(contact -> contact instanceof Vendor)
                    .map(contact -> (Vendor) contact)
                    .forEach(event::addVendor);

            boolean hasVendors = jsonAdaptedEvent.getVendorIds().stream().anyMatch(Objects::nonNull);
            if (!hasVendors) {
                throw new IllegalValueException(MESSAGE_NO_VENDOR);
            }

            checkAssociation(eventIdsTable, event);

            addressBook.addEvent(event);
            int eventId = event.getEventId().id;
            if (eventId > maxEventId) {
                maxEventId = eventId;
            }
        }

        if (maxContactId >= nextContactId) {
            nextContactId = maxContactId + 1;
        }
        if (maxEventId >= nextEventId) {
            nextEventId = maxEventId + 1;
        }

        AddressBook.setNextContactId(nextContactId);
        AddressBook.setNextEventId(nextEventId);

        return addressBook;
    }

    private void checkAssociation(Map<Integer, Set<Id>> eventIdsTable, Event event) throws IllegalValueException {
        List<Id> clientIds = event.getClientIds();
        for (Id id: clientIds) {
            Set<Id> eventIds = eventIdsTable.get(id.id);
            if (!eventIds.contains(event.getEventId())) {
                throw new IllegalValueException(MESSAGE_ASSOCIATION_MISMATCH);
            }
        }
        List<Id> vendorIds = event.getVendorIds();
        for (Id id: vendorIds) {
            Set<Id> eventIds = eventIdsTable.get(id.id);
            if (!eventIds.contains(event.getEventId())) {
                throw new IllegalValueException(MESSAGE_ASSOCIATION_MISMATCH);
            }
        }
    }
}
