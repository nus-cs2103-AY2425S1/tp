package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Module;
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
     * Returns the user prefs' EduContacts file path.
     */
    Path getEduContactsFilePath();

    /**
     * Sets the user prefs' EduContacts file path.
     */
    void setEduContactsFilePath(Path eduContactsFilePath);

    /**
     * Replaces EduContacts data with the data in {@code eduContacts}.
     */
    void setEduContacts(ReadOnlyEduContacts eduContacts);

    /** Returns the EduContacts */
    ReadOnlyEduContacts getEduContacts();

    /**
     * Returns true if a person with the same identity as {@code person} exists in EduContacts.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in EduContacts.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given module for the given person.
     * The person and the corresponding module must exist in EduContacts.
     */
    void deleteModule(Person target, Module module);

    /**
     * Adds the given person.
     * {@code person} must not already exist in EduContacts.
     */
    void addPerson(Person person);

    /**
     * Adds the given module to the given person.
     */
    void addModule(Person target, Module module);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in EduContacts.
     * The person identity of {@code editedPerson} must not be the same as another existing person in EduContacts.
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
     * Displays the given person {@code personToDisplay} and their details.
     * {@code target} must exist in EduContacts.
     */
    void setPersonToDisplay(Person personToDisplay);

    /**
     * Returns the Person object to display.
     */
    Person getPersonToDisplay();

}
