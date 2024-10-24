package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.id.counter.list.IdCounterList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueEventList events;
    private final IdCounterList idCounterList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        events = new UniqueEventList();
        idCounterList = new IdCounterList();
    }

    public AddressBook() {}

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
     * Replaces the contents of the ID counter list with {@code idCounterList}.
     * {@code idCounterList} should contain the largest unique person/event ID that currently exists.
     */
    public void setIdCounterList(IdCounterList idCounterList) {
        this.idCounterList.setPersonIdCounter(idCounterList.getPersonIdCounter());
        this.idCounterList.setEventIdCounter(idCounterList.getEventIdCounter());
    }

    /**
     * Returns true if an event with the given ID exists in the address book.
     */
    public boolean hasEventById(int eventId) {
        return events.containsId(eventId);
    }

    /**
     * Retrieves an event by its ID.
     * @throws IllegalArgumentException if the event does not exist.
     */
    public Event getEventById(int eventId) {
        return events.getById(eventId);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);
        events.setEvent(target, editedEvent);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setEvents(newData.getEventList());
        setIdCounterList(newData.getIdCounterList());
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
     * Gets all the persons whose names are the same (case-insensitive) as the given argument.
     */
    public List<Person> findPersonsWithName(Name name) {
        requireNonNull(name);
        return persons.getPersonsWithName(name);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book and must have a unique ID.
     */
    public void addPerson(Person p) {
        assert p.getId() != -1 : "Person added should not have an ID of -1";
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        assert editedPerson.getId() != -1 : "Edited person should not have an ID of -1.";

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
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
     * The event must not already exist in the address book and must have a unique ID.
     */
    public void addEvent(Event event) {
        assert event.getEventId() != -1 : "Event added should not have an ID of -1.";
        events.add(event);
    }

    //// ID counter-level operations

    /**
     * Generates a new unique person ID.
     */
    public int generateNewPersonId() {
        int newPersonId = idCounterList.generatePersonId();
        while (persons.containsId(newPersonId)) {
            newPersonId = idCounterList.generatePersonId();
        }
        return newPersonId;
    }

    /**
     * Generates a new unique event ID.
     */
    public int generateNewEventId() {
        int newEventId = idCounterList.generateEventId();
        while (events.containsId(newEventId)) {
            newEventId = idCounterList.generateEventId();
        }
        return newEventId;
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
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
    public IdCounterList getIdCounterList() {
        return new IdCounterList(idCounterList.getPersonIdCounter(), idCounterList.getEventIdCounter());
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
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
