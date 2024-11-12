package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that evaluates to true if the person is a student */
    Predicate<Person> PREDICATE_SHOW_ALL_STUDENTS = person -> person instanceof Student;

    /** {@code Predicate} that evaluates to true if the person is a parent */
    Predicate<Person> PREDICATE_SHOW_ALL_PARENTS = person -> person instanceof Parent;

    /** {@code Predicate} that finds unarchived persons*/
    Predicate<Person> PREDICATE_SHOW_UNARCHIVED_PERSONS = person -> !person.isArchived();

    /** {@code Predicate} that finds archived persons*/
    Predicate<Person> PREDICATE_SHOW_ARCHIVED_PERSONS = person -> person.isArchived();

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

    /**
     * Pins the given person to the top of the address book.
     */
    void pinPerson(Person person);

    /**
     * Unpins the given person to the top of the address book.
     */
    void unpinPerson(Person person);

    /**
     * Archives the given person to the top of the address book.
     */
    void archivePerson(Person person);

    /**
     * Unarchives the given person to the top of the address book.
     */
    void unarchivePerson(Person person);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     *  Returns the predicate of the current filtered person list
     */
    Predicate<Person> getFilteredPersonListPredicate();

    /**
     * Sorts the person list to put all pinned persons at the top of the list.
     */
    void sortByPin();

    /**
     * Sorts the person list according to their name.
     */
    void sortByName();

    Person personFromName(Name name);
}
