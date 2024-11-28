package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.command.Commands;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.statistics.AddressBookStatistics;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final Commands commandList;
    private final UniquePersonList persons;
    private final AddressBookStatistics statistics;
    private Comparator<Person> sortComparator = null;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        commandList = new Commands();
        persons = new UniquePersonList();
        statistics = new AddressBookStatistics();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetStatistics();
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
     * Returns the {@code statistics} tracking the current AddressBook.
     */
    public AddressBookStatistics getStatistics() {
        return this.statistics;
    }

    /**
     * Resets the existing statistics of this {@code AddressBook}.
     */
    public void resetStatistics() {
        this.statistics.reset();
    }

    /**
     * Refreshes the {@code addressBookStatistics}.
     */
    public void refreshStatistics(ObservableList<Person> personList) {
        this.statistics.processPersonListData(personList);
    }

    /**
     * Refreshes the {@code addressBookStatistics}.
     */
    public void refreshStatistics() {
        this.statistics.processPersonListData(this.persons.asUnmodifiableObservableList());
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        List<Person> latestPersonList = newData.getPersonList();
        setPersons(latestPersonList);
        refreshStatistics();
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
     * Returns true if a property with the same identity as {@code property} exists in the address book.
     */
    public boolean hasSellProperty(Index index, Property property) {
        requireNonNull(property);
        // There's no get method for ObservableList, so we can't get the property at the index
        // Person specificPerson = persons.get(index.getZeroBased());
        return false;
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        statistics.processPersonData(p);
        persons.add(p);
    }

    public void addSellProperty(Property p) {
        //properties.add(p);
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

    /**
     * Sorts the list of contacts by specified field and order with pinned contacts first.
     */
    public void sortPersonList() {
        List<Person> pinnedPersons = persons.filtered(Person::isPinned);
        List<Person> unpinnedPersons = persons.filtered(person -> !person.isPinned());

        if (sortComparator != null) {
            pinnedPersons.sort(sortComparator);
            unpinnedPersons.sort(sortComparator);
        }

        persons.setPersons(pinnedPersons);
        persons.addAll(unpinnedPersons);
    }

    /**
     * Sorts the list of contacts by specified field and order.
     */
    public void setSortComparator(Comparator<Person> comparator) {
        this.sortComparator = comparator;
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

    public Commands getCommandList() {
        return this.commandList;
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
