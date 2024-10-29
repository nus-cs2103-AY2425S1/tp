package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.tag.ActiveTags;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

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

    ObservableMap<String, String> getTagColorMap();

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

    /**
     * Adds a new Wedding specified by a name and date to the AddressBook.
     * The name of the new Wedding must not be the same as another existing Wedding in the AddressBook.
     * @param wedding the Wedding to add
     */
    void addWedding(Wedding wedding);

    /**
     * Returns true if a wedding with the same identity as {@code wedding} exists in the address book.
     */
    boolean hasWedding(Wedding wedding);

    /**
     * Removes a Wedding specified by name.
     * The Wedding must already exist in the AddressBook.
     * @param wedding the Wedding to remove
     */
    void removeWedding(Wedding wedding);

    /**
     * Replaces the specified wedding with a new one
     * @param wedding
     * @param editedWedding
     */
    void setWedding(Wedding wedding, Wedding editedWedding);

    /**
     * Assigns a Person to a Wedding specified by name.
     * Both the Person and Wedding must already exist in the AddressBook.
     * The Person must not be currently assigned to the Wedding.
     * @param wedding
     * @param toAssign
     */
    void assignPerson(Wedding wedding, Person toAssign);

    /**
     * Unassigns a Person from a Wedding specified by name.
     * Both the Person and Wedding must already exist in the AddressBook.
     * The Person must be currently assigned to the Wedding.
     * @param wedding
     * @param toUnassign
     */
    void unassignPerson(Wedding wedding, Person toUnassign);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns an unmodifiable view of the filtered wedding list.
     * @return
     */
    ObservableList<Wedding> getFilteredWeddingList();

    /**
     * Returns a HashMap of the current active Tags and their occurences
     * @return
     */

    ActiveTags getActiveTags();

    /**
     * Returns a WeddingName of the currently displayed wedding, or null if no wedding is being viewed
     * WeddingName is used because Wedding objects are immutable could change when a wedding is edited,
     * while WeddingName is used to determine uniqueness of weddings
     * @return currently displayed wedding, or null
     */

    WeddingName getCurrentWeddingName();

    /**
     * Sets currentWeddingName attribute to a specified WeddingName or null
     */
    void setCurrentWeddingName(WeddingName weddingName);

    /**
     * Sorts the list of persons in the address book according to the given comparator.
     */
    void sortPersonList(Comparator<Person> comparator);
}
