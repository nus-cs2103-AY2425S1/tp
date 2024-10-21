package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.UniqueWeddingList;
import seedu.address.model.wedding.Wedding;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueWeddingList weddings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        weddings = new UniqueWeddingList();
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
     * Replaces the contents of the wedding list with {@code weddings}.
     * {@code weddings} must not contain duplicate weddings.
     */
    public void setWeddings(List<Wedding> weddings) {
        this.weddings.setWeddings(weddings);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setWeddings(newData.getWeddingList());

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
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// Wedding level operations
    /**
     * Returns true if a wedding with the same identity as {@code wedding} exists in the address book.
     */
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddings.contains(wedding);
    }

    public void addWedding(Wedding wedding) {
        weddings.addWedding(wedding);
    }

    public void removeWedding(Wedding wedding) {
        weddings.removeWedding(wedding);
    }

    public void setWedding(Wedding wedding, Wedding editedWedding) {
        weddings.setWedding(wedding, editedWedding);
    }

    /**
     * Assings a Person to a Wedding
     * @param wedding
     * @param person
     */
    public void assignPerson(Wedding wedding, Person person) {
        requireNonNull(wedding);
        requireNonNull(person);

        weddings.assignToWedding(wedding, person);
    }

    /**
     * Unassigns a Person from a Wedding
     * @param wedding
     * @param person
     */
    public void unassignPerson(Wedding wedding, Person person) {
        requireNonNull(wedding);
        requireNonNull(person);

        weddings.unassignFromWedding(wedding, person);
    }

    /**
     * Returns a HashMap of the currently used Tags and their occurrences
     * @return
     */
    public HashMap<Tag, Integer> findTagOccurrences() {
        HashMap<Tag, Integer> result = new HashMap<>();
        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            Set<Tag> tagSet = p.getTags();
            for (Tag t : tagSet) {
                if (result.containsKey(t)) {
                    Integer i = result.get(t);
                    result.replace(t, i + 1);
                } else {
                    result.put(t, 1);
                }
            }
        }

        return result;
    }

    /**
     * Sorts the list of persons in the address book according to the given comparator.
     */
    public void sortPersons(Comparator<Person> comparator) {
        requireNonNull(comparator);
        FXCollections.sort(persons.asModifiableObservableList(), comparator);
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

    public ObservableList<Wedding> getWeddingList() {
        return weddings.asUnmodifiableObservableList();
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
