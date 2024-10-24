package seedu.ddd.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.ReadOnlyAddressBook;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_CLIENT = "Clients list contains duplicate client(s).";
    public static final String MESSAGE_DUPLICATE_VENDOR = "Vendors list contains duplicate vendor(s).";
    public static final String MESSAGE_NO_CLIENT = "Event does not have a valid client.";
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
        HashMap<Integer, Set<EventId>> eventIdsTable = new HashMap<>();
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
            int contactId = contact.getId().contactId;
            eventIdsTable.put(contactId, jsonAdaptedContact.getEventIds());
            addressBook.addContact(contact);
            if (contactId > maxContactId) {
                maxContactId = contactId;
            }
        }

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            List<ContactId> clientIds = jsonAdaptedEvent.getClientIds();
            if (clientIds == null || clientIds.isEmpty()) {
                throw new IllegalValueException(MESSAGE_NO_CLIENT);
            }
            for (ContactId clientId: clientIds) {
                addContactToEvent(event, clientId, addressBook, eventIdsTable, true);
            }
            List<ContactId> vendorIds = jsonAdaptedEvent.getVendorIds();
            for (ContactId vendorId: vendorIds) {
                addContactToEvent(event, vendorId, addressBook, eventIdsTable, false);
            }
            addressBook.addEvent(event);
            int eventId = event.getEventId().eventId;
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

    private void addContactToEvent(Event event, ContactId contactId, AddressBook addressBook,
            Map<Integer, Set<EventId>> eventIdsTable, boolean isClient) throws IllegalValueException {
        Contact contact = addressBook.getContact(contactId);
        if (contact == null) {
            throw new IllegalValueException(MESSAGE_CONTACT_NOT_CREATED);
        }

        Set<EventId> eventIds = eventIdsTable.get(contact.getId().contactId);
        if (!eventIds.contains(event.getEventId())) {
            throw new IllegalValueException(MESSAGE_ASSOCIATION_MISMATCH);
        }
        if (isClient) {
            assert contact instanceof Client;
            event.addClient((Client) contact);
        } else {
            assert contact instanceof Vendor;
            event.addVendor((Vendor) contact);
        }
    }
}
