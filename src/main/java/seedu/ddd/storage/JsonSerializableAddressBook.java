package seedu.ddd.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.AddressBook;
import seedu.ddd.model.ReadOnlyAddressBook;
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
    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";
    public static final String MESSAGE_CONTACT_NOT_CREATED = "Event contains contact(s) that have not been created.";
    private final List<JsonAdaptedContact> contacts = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final int nextContactId;
    private final int nextEventId;

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
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact = jsonAdaptedContact.toModelType();
            if (addressBook.hasContact(contact)) {
                if (contact instanceof Client) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_CLIENT);
                } else if (contact instanceof Vendor) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_VENDOR);
                }
            }
            addressBook.addContact(contact);
        }

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            List<Contact> contacts = event.getContacts();
            for (Contact contact: contacts) {
                if (!addressBook.hasContact(contact)) {
                    throw new IllegalValueException(MESSAGE_CONTACT_NOT_CREATED);
                }
            }
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            addressBook.addEvent(event);
        }

        AddressBook.setNextContactId(nextContactId);
        AddressBook.setNextEventId(nextEventId);

        return addressBook;
    }
}
