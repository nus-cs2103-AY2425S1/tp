package seedu.sellsavvy.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.sellsavvy.commons.core.GuiSettings;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Creates a copy of the entire {@code Model}.
     */
    Model createCopy();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //TODO: change it to getSelectedPersonProperty
    /** Returns an unmodifiable view of selected person */
    ReadOnlyObjectProperty<Person> getSelectedPerson();

    //TODO: change it to getSelectedPerson
    /** Returns the selected person displayed*/
    Person getSelectedPerson2();

    /** Returns the {@code OrderList} displayed*/
    FilteredList<Order> getFilteredOrderList();

    /**
     * Updates the selected person whose orders are displayed.
     */
    void updateSelectedPerson(Person person);

    /**
     * Checks if the given {@code person} is the selected person whose orders are displayed.
     */
    boolean isSelectedPerson(Person person);

    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the displayed order list.
     */
    void setOrder(Order target, Order editedOrder);

    /**
     * Returns a {@code Person} in the {@code UniquePersonList} equivalent to target Person given.
     * Returns null if target is null.
     */
    Person findEquivalentPerson(Person person);
}
