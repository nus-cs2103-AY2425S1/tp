package bizbook.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import bizbook.commons.util.ToStringBuilder;
import bizbook.model.person.Person;
import bizbook.model.person.UniquePersonList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePersonList pinnedPersons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        pinnedPersons = new UniquePersonList();
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        // Empty the pinned person list since we are reseting the addressbook
        setPinnedPersons(newData.getPinnedPersonList());
        setPersons(newData.getPersonList());
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
        if (isPinned(target)) {
            pinnedPersons.setPerson(target, editedPerson);
        }
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        if (isPinned(key)) {
            removePinnedPerson(key);
        }
    }

    //=========== Pinned Person List Accessors ===============================================================

    /**
     * Returns true if a person with the same identity as {@code person} exists in the pinned list.
     */
    public boolean isPinned(Person person) {
        requireNonNull(person);
        return pinnedPersons.contains(person);
    }

    /**
     * Returns an unmodifiable view of the pinned person list.
     */
    public ObservableList<Person> getPinnedPersonList() {
        return pinnedPersons.asUnmodifiableObservableList();
    }

    /**
     * Replaces the contents of the pinned list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPinnedPersons(List<Person> persons) {
        this.pinnedPersons.setPersons(persons);
    }

    /**
     * Adds the {@code person} in the pinned contact list.
     *
     * @param person The {@code person} in the contact list to be pinned.
     */
    public void addPinnedPerson(Person person) {
        this.pinnedPersons.add(person);
    }

    /**
     * Remove the {@code person} in the pinned contact list.
     *
     * @param person The {@code person} in the contact list to be unpinned.
     */
    public void removePinnedPerson(Person person) {
        this.pinnedPersons.remove(person);
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
                && pinnedPersons.equals(otherAddressBook.pinnedPersons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
