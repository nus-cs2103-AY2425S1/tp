package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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

    /**
     * Saves the current state of the address book.
     * This allows the current state to be restored via undo or redo operations.
     */
    void saveAddressBook();

    /**
     * Restores the previous state of the address book.
     * This allows the user to undo the last change made to the address book.
     *
     * @throws seedu.address.model.VersionedAddressBook.InvalidUndoException if there is no previous state to undo to.
     */
    void undoAddressBook();

    /**
     * Restores the next state of the address book that was undone.
     * This allows the user to redo the last undone change.
     *
     * @throws seedu.address.model.VersionedAddressBook.InvalidRedoException if there is no state to redo to.
     */
    void redoAddressBook();

    /**
     * Returns {@code true} if there are states to undo in the address book.
     *
     * @return {@code true} if undo is possible, {@code false} otherwise.
     */
    boolean canUndoAddressBook();

    /**
     * Returns {@code true} if there are states to redo in the address book.
     *
     * @return {@code true} if redo is possible, {@code false} otherwise.
     */
    boolean canRedoAddressBook();
}
