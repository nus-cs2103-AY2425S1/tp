package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.ShortCut;
import seedu.address.model.shortcut.UniqueShortCutList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueOrderList orders;
    private final UniqueShortCutList shortcuts;


    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        orders = new UniqueOrderList();
        shortcuts = new UniqueShortCutList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    public void setOrders(List<Order> orders) {
        this.orders.setOrders(orders);
    }
    public void setShortcuts(List<ShortCut> shortcuts) {
        this.shortcuts.setShortCuts(shortcuts);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setOrders(newData.getOrderList());
        setShortcuts(newData.getShortCutList());
    }

    // person-level operations

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

    public Person findPersonByName(Name name) {
        return persons.findPersonByName(name);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public boolean hasOrder(Order order) {
        return orders.contains(order);
    }
    // shortcuts methods
    public void addShortCut(ShortCut shortcut) {
        shortcuts.add(shortcut);
    }
    public void removeShortCut(ShortCut shortcut) {
        shortcuts.remove(shortcut);
    }
    public boolean hasShortCut(ShortCut shortcut) {
        return shortcuts.contains(shortcut);
    }
    public boolean hasAlias(Alias alias) {
        return shortcuts.containsAlias(alias);
    }
    // util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("shortcuts", shortcuts)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList(Set<Tag> tagList) {
        return persons.asUnmodifiableFilteredObservableList(tagList);
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asUnmodifiableObservableList();
    }

    /**
     * Returns a list of persons in the address book that match the given postal code.
     *
     * @param postalCode The postal code to match against the persons' postal codes.
     * @return A list of persons whose postal code matches the specified postal code.
     *         The list is unmodifiable and contains all matching persons in the address book.
     */
    public List<Person> getPersonsByPostalCode(PostalCode postalCode) {
        requireNonNull(postalCode);
        return persons.asUnmodifiableObservableList().stream()
                .filter(person -> person.getPostalCode().equals(postalCode))
                .collect(Collectors.toList());
    }
    @Override
    public ObservableList<ShortCut> getShortCutList() {
        return shortcuts.asUnmodifiableObservableList();
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
                && orders.equals(otherAddressBook.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, orders, shortcuts);
    }
}
