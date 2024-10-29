package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.types.common.LinkedPersonsEntry;
import seedu.address.model.types.common.PersonEventManager;
import seedu.address.model.types.event.Event;
import seedu.address.model.types.event.UniqueEventList;
import seedu.address.model.types.person.Person;
import seedu.address.model.types.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson and .isSameEvent comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    private final UniqueEventList events;

    private PersonEventManager personEventManager;

    /**
     * Creates an AddressBook
     */
    public AddressBook() {
        persons = new UniquePersonList();
        events = new UniqueEventList();
        personEventManager = new PersonEventManager();
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Replaces the contents of the person-event manager with {@code personEventManager}.
     * {@code personEventManager} must not contain duplicate events.
     */
    public void setPersonEventManager(PersonEventManager personEventManager) {
        this.personEventManager = personEventManager;
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setEvents(newData.getEventList());
        setPersonEventManager(newData.getPersonEventManager());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        personEventManager.setPersonForAllEvents(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        personEventManager.removePersonFromAllEvents(key);
    }

    /**
     * Returns true if a person is linked to an event.
     */
    public boolean isPersonLinkedToEvent(Person person, Event event) {
        requireAllNonNull(person, event);
        return personEventManager.isPersonLinkedToEvent(person, event);
    }

    /**
     * Links a person to an event.
     */
    public void linkPersonToEvent(Person person, Event event) {
        requireAllNonNull(person, event);
        personEventManager.addPersonToEvent(person, event);
    }

    //// event-level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event e) {
        events.add(e);
        personEventManager.addEvent(e);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
        personEventManager.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        events.remove(key);
        personEventManager.removeEvent(key);
    }

    public Event getEventByName(Event event) {
        requireNonNull(event);
        return personEventManager.getEventByName(event);
    }

    /** Resorts Events */
    public void reSortEvents() {
        events.sortEvents();
    };

    /**
     * Adds a linked persons entry to the personEventManager.
     */
    public void addLinkedPersonsEntry(LinkedPersonsEntry linkedPersonsEntry) {
        personEventManager.addLinkedPersonsEntry(linkedPersonsEntry);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("events", events)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public PersonEventManager getPersonEventManager() {
        return personEventManager;
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
        return persons.equals(otherAddressBook.persons)
                && events.equals(otherAddressBook.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, events);
    }


}
