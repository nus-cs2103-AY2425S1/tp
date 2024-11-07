package seedu.address.model;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.ReadOnlyEventManager;
import seedu.address.model.person.Person;

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
     * Returns the number of persons in the address book with the same identity as {@code person}.
     */
    int countSamePersons(Person person);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same phone number as {@code person} exists in the address book.
     */
    boolean hasPhone(Person person);

    /**
     * Returns true if a person with the same email as {@code person} exists in the address book.
     */
    boolean hasEmail(Person person);

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


    /**
     * Reset the EventManager Data
     */
    void setEventManager(ReadOnlyEventManager eventManager);

    /**
     * Return the EventManager
     */
    EventManager getEventManager();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Sets the search mode to the given {@code searchMode}.
     */
    void setSearchMode(boolean searchMode);

    /**
     * Returns the search mode.
     */
    boolean getSearchMode();

    /**
     * Returns the last predicate used to filter the person list.
     */
    Predicate<Person> getLastPredicate();

    /**
     * Get all persons in the address book.
     */
    ObservableList<Person> getAllPersons();

    /**
     * Returns the search mode property.
     */
    BooleanProperty searchModeProperty();

    /**
     * Returns the set of persons that are excluded from the search.
     */
    Set<Person> getExcludedPersons();

    /**
     * Excludes a Person from the search.
     */
    void excludePerson(Person person);

    void clearExcludedPersons();

    void updateFilteredListWithExclusions(Predicate<Person> predicate);


    String[] findSameField(Person person);
}
