package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Meetings;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final Meetings meetings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        meetings = new Meetings();
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
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setMeetings(List<Meeting> meetings) {
        this.meetings.setInternalList(meetings);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setMeetings(newData.getMeetingList());
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
    public void setPerson(Person target, Person editedPerson) throws CommandException {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        deletePersonMeetings(key);
        persons.remove(key);
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

    public void addMeeting(Meeting m) {
        meetings.addMeeting(m);
    }

    public void deleteMeeting(Meeting m) {
        meetings.deleteMeeting(m);
    }

    public Meeting getMeeting(int index) {
        return meetings.getMeeting(index);
    }

    /**
     * Deletes all meetings that contains (@code p).
     */
    public void deletePersonMeetings(Person p) {
        Meetings personMeetings = p.getMeetings();
        for (int i = 0; i < personMeetings.getMeetingsCount(); i++) {
            Meeting m = personMeetings.getMeeting(i);
            for (int j = 0; j < meetings.getMeetingsCount(); j++) {
                if (m.equals(meetings.getMeeting(j))) {
                    Meeting toDelete = meetings.getMeeting(j);
                    deleteMeeting(toDelete);
                }
            }
        }
    }

    /**
     * Returns true if addressbook already contains (@code meeting).
     */
    public boolean hasMeeting(Meeting m) {
        requireNonNull(m);
        return meetings.contains(m);
    }

    /**
     * Replaces the given meeting {@code target} in the list with {@code editedMeeting}.
     * {@code target} must exist in the address book.
     * The meeting identity of {@code editedMeeting} must not be the same as another existing meeting in address book.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireNonNull(editedMeeting);
        meetings.setMeeting(target, editedMeeting);
    }

    public String listMeetings() {
        return meetings.toString();
    }

    public int getMeetingSize() {
        return meetings.getMeetingsCount();
    }

    public ObservableList<Meeting> getMeetingList() {
        return meetings.getInternalList();
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
