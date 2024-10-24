package seedu.ddd.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.UniqueContactList;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.UniqueEventList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {
    private static int nextContactId;
    private static int nextEventId;

    /*
    * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
    * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    *
    * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
    *   among constructors.
    */
    private final UniqueContactList contacts;
    {
        contacts = new UniqueContactList();
    }

    private final UniqueEventList events;
    {
        events = new UniqueEventList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Contacts in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setContacts(newData.getContactList());
        setEvents(newData.getEventList());
    }

    //// contact-level operations

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.contains(contact);
    }

    /**
     * Returns true if a client with the same id as {@code id} exists in the address book.
     */
    public boolean hasClientId(ContactId contactId) {
        requireNonNull(contactId);
        return contacts.containsClientId(contactId);
    }

    /**
     * Returns true if a vendor with the same id as {@code id} exists in the address book.
     */
    public boolean hasVendorId(ContactId contactId) {
        requireNonNull(contactId);
        return contacts.containsVendorId(contactId);
    }

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds a contact to the address book.
     * If contact has not been created or does not exist, a null object will be returned.
     */
    public Contact getContact(ContactId contactId) {
        return contacts.get(contactId);
    }

    /**
     * Adds a contact to the address book.
     * The contact must not already exist in the address book.
     */
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    /**
     * Adds a event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing person
     * in the address book.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);
        contacts.setContact(target, editedContact);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event identity of {@code editedEvent} must not be the same as another existing event
     * in the address book.
     */
    public void setEvent(Event target, Event editedContact) {
        requireNonNull(editedContact);
        //method not implemented yet
        //events.setEvents(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeContact(Contact key) {
        contacts.remove(key);
    }

    /**
     * Removes {@code targetEvent} from this {@code AddressBook}.
     * {@code targetEvent} must exist in the address book.
     */
    public void removeEvent(Event targetEvent) {
        events.remove(targetEvent);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("contacts", contacts)
                .add("events", events)
                .toString();
    }

    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    /**
     * Sets the {@code nextContactId} for this {@code AddressBook}.
     */
    public static void setNextContactId(int nextContactId) {
        AddressBook.nextContactId = nextContactId;
    }

    /**
     * Increments the {@code nextContactId} for this {@code AddressBook} by 1.
     */
    public static void incrementNextContactId() {
        AddressBook.nextContactId += 1;
    }

    /**
     * Retrieves the {@code nextContactId} from this {@code AddressBook}.
     */
    public static int getNextContactId() {
        return AddressBook.nextContactId;
    }

    /**
     * Sets the {@code nextEventId} for this {@code AddressBook}.
     */
    public static void setNextEventId(int nextEventId) {
        AddressBook.nextEventId = nextEventId;
    }

    /**
     * Increments the {@code nextEventId} for this {@code AddressBook} by 1.
     */
    public static void incrementNextEventId() {
        AddressBook.nextEventId += 1;
    }

    /**
     * Retrieves the {@code nextEventId} from this {@code AddressBook}.
     */
    public static int getNextEventId() {
        return AddressBook.nextEventId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return contacts.equals(otherAddressBook.contacts) && events.equals(otherAddressBook.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.contacts, this.events);
    }
}
