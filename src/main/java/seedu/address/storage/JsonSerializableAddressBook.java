package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Contact;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_CONTACT = "Contacts list contains duplicate contact(s).";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Contacts list contains duplicate emails, handles, or (name && nickname)";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("contacts") List<JsonAdaptedContact> contacts) {
        this.contacts.addAll(contacts);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        contacts.addAll(source.getContactList().stream()
                .map(seedu.address.storage.JsonAdaptedContact::new).collect(Collectors.toList()));
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
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONTACT);
            }

            if (addressBook.hasDuplicateFields(contact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FIELDS);
            }
            addressBook.addContact(contact);
        }
        return addressBook;
    }

}
