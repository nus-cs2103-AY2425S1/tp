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

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_CLIENT = "Clients list contains duplicate client(s).";
    public static final String MESSAGE_DUPLICATE_VENDOR = "Vendors list contains duplicate vendor(s).";
    private final List<JsonAdaptedContact> contacts = new ArrayList<>();
    private int nextId;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("contacts") List<JsonAdaptedContact> contacts,
        @JsonProperty("nextId") int nextId) {
        this.contacts.addAll(contacts);
        this.nextId = nextId;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContactFactory::create)
                .collect(Collectors.toList()));
        nextId = AddressBook.getNextId();
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
        AddressBook.setNextId(nextId);
        return addressBook;
    }
}
