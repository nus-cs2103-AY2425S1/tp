package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.concert.UniqueConcertContactList;
import seedu.address.model.concert.UniqueConcertList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed
 * (by .isSamePerson, isSameConcert, isSameConcertContact comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueConcertList concerts;
    private final UniqueConcertContactList concertContacts;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid
     * duplication between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html Note that non-static init
     * blocks are not recommended to use. There are other ways to avoid duplication among
     * constructors.
     */
    {
        persons = new UniquePersonList();
        concerts = new UniqueConcertList();
        concertContacts = new UniqueConcertContactList();
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
     * Replaces the contents of the person list with {@code persons}. {@code persons} must not
     * contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the concert list with {@code concerts}. {@code concert} must not
     * contain duplicate concerts.
     */
    public void setConcerts(List<Concert> concerts) {
        this.concerts.setConcerts(concerts);
    }

    /**
     * Replaces the contents of the concertContact list with {@code concertContacts}. {@code concertContacts} must not
     * contain duplicate concertContacts.
     */
    public void setConcertContacts(List<ConcertContact> concertContacts) {
        this.concertContacts.setConcertContacts(concertContacts);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setConcerts(newData.getConcertList());
        setConcertContacts(newData.getConcertContactList());
    }

    //// concert-level operations
    /**
     * Returns true if a concert with the same identity as {@code concert} exists.
     *
     * @param concert
     * @return
     */
    public boolean hasConcert(Concert concert) {
        requireNonNull(concert);
        return concerts.contains(concert);
    }

    /**
     * Adds a concert.
     *
     * @param concert
     */
    public void addConcert(Concert concert) {
        concerts.add(concert);
    }

    /**
     * Replaces the given concert {@code target} in the list with {@code editedConcert}.
     * {@code target} must exist in the address book. The concert identity of {@code editedConcert}
     * must not be the same as another existing concert in the address book.
     *
     * @param target
     * @param editedConcert
     */
    public void setConcert(Concert target, Concert editedConcert) {
        requireNonNull(editedConcert);

        concerts.setConcert(target, editedConcert);
    }

    //// concertContact-level operations
    /**
     * Returns true if a concertContact with the same identity as {@code concertContact} exists.
     *
     * @param concertContact concertContact to check
     * @return true if address-book contains concertContact
     */
    public boolean hasConcertContact(ConcertContact concertContact) {
        requireNonNull(concertContact);
        return concertContacts.contains(concertContact);
    }

    /**
     * Returns true if a concertContact with the same person and concert as {@code person}  and {@code concert} exists.
     *
     * @param person person to check for
     * @param concert concert to check for
     * @return true if address-book contains concertContact
     */
    public boolean hasConcertContact(Person person, Concert concert) {
        requireAllNonNull(person, concert);
        return concertContacts.contains(person, concert);
    }

    /**
     * Adds a concertContact.
     *
     * @param concertContact concertContact to add
     */
    public void addConcertContact(ConcertContact concertContact) {
        concertContacts.add(concertContact);
    }

    /**
     * Replaces the given concertContact {@code target} in the list with {@code editedConcertContact}.
     * {@code target} must exist in the address book. The concertContact identity of {@code editedConcertContact}
     * must not be the same as another existing concertContact in the address book.
     *
     * @param target concertContact to replace
     * @param editedConcertContact concertContact that replaces existing one
     */
    public void setConcertContact(ConcertContact target, ConcertContact editedConcertContact) {
        requireNonNull(editedConcertContact);

        concertContacts.setConcertContact(target, editedConcertContact);
    }

    /**
     * Removes the given {@code concertContact}
     * from this {@code AddressBook}. {@code concertContact} must exist in the address book.
     */
    public void removeConcertContact(ConcertContact concertContactKey) {
        concertContacts.remove(concertContactKey);
    }

    /**
     * Removes all concertContacts associated to {@code concertKey} from this {@code AddressBook}.
     */
    public void removeConcertContact(Concert concertKey) {
        concertContacts.remove(concertKey);
    }

    /**
     * Removes all concertContacts associated to {@code personKey} from this {@code AddressBook}.
     */
    public void removeConcertContact(Person personKey) {
        concertContacts.remove(personKey);
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
     * Adds a person to the address book. The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book. The person identity of {@code editedPerson}
     * must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address
     * book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address
     * book.
     */
    public void removeConcert(Concert key) {
        concerts.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("persons", persons).add("concerts", concerts)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Concert> getConcertList() {
        return concerts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<ConcertContact> getConcertContactList() {
        return concertContacts.asUnmodifiableObservableList();
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
                && concerts.equals(otherAddressBook.concerts)
                && concertContacts.equals(otherAddressBook.concertContacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons.hashCode(), concerts.hashCode());
    }
}
