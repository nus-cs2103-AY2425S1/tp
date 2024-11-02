package bizbook.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import bizbook.commons.core.GuiSettings;
import bizbook.model.person.Person;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

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
     * Returns the person who will be focused on.
     */
    ObjectProperty<Person> getFocusedPerson();

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
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the pinned list.
     */
    public boolean isPinned(Person person);

    /**
     * Adds the {@code person} in the pinned contact list.
     *
     * @param person The {@code person} in the contact list to be pinned.
     */
    public void pinPerson(Person person);

    /**
     * Remove the {@code person} in the pinned contact list.
     *
     * @param person The {@code person} in the contact list to be unpinned.
     */
    public void unpinPerson(Person person);

    /**
     * Saves the current state of the {@code AddressBook} into a version history list.
     */
    void saveAddressBookVersion();

    /**
     * Reverts the {@code AddressBook} to the most recent saved version.
     */
    void revertAddressBookVersion();
}
