package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingNameContainsKeywordsPredicate;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Wedding> PREDICATE_SHOW_ALL_WEDDINGS = unused -> true;

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
     * Returns the user prefs' wedding book file path.
     */
    Path getWeddingBookFilePath();

    /**
     * Sets the user prefs' wedding book file path.
     */
    void setWeddingBookFilePath(Path weddingBookFilePath);

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
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Updates the filter of the filtered person list to filter by the given {@code JobContainsKeywordsPredicate}. */
    void updateFilteredPersonList(JobContainsKeywordsPredicate predicate);

    /**
     * Replaces wedding book data with the data in {@code weddingBook}.
     */
    void setWeddingBook(ReadOnlyWeddingBook weddingBook);

    /** Returns the WeddingBook */
    ReadOnlyWeddingBook getWeddingBook();

    /**
     * Returns true if a wedding with the same identity as {@code wedding} exists in the wedding book.
     */
    boolean hasWedding(Wedding wedding);

    /**
     * Deletes the given wedding.
     * The wedding must exist in the wedding book.
     */
    void deleteWedding(Wedding target);

    /**
     * Adds the given wedding.
     * {@code wedding} must not already exist in the wedding book.
     */
    void addWedding(Wedding wedding);

    /**
     * Replaces the given wedding {@code target} with {@code editedWedding}.
     * {@code target} must exist in the wedding book.
     * The identity of {@code editedWedding} must not be the same as another existing wedding in the wedding book.
     */
    void setWedding(Wedding target, Wedding editedWedding);

    /** Returns an unmodifiable view of the filtered wedding list */
    ObservableList<Wedding> getFilteredWeddingList();

    /**
     * Updates the filter of the filtered wedding list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWeddingList(Predicate<Wedding> predicate);

    /** Updates the filter of the filtered wedding list to filter by the given {@code JobContainsKeywordsPredicate}. */
    void updateFilteredWeddingList(WeddingNameContainsKeywordsPredicate predicate);
}
