package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<Tutorial> PREDICATE_SHOW_ALL_TUTORIALS = unused -> true;

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


    //// Person-related methods ////

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

    /** Returns an unmodifiable view of the entire person list */
    ObservableList<Person> getPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);



    //// Tutorial-related methods ////

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in the address book.
     */
    boolean hasTutorial(Tutorial tutorial);

    /**
     * Closes/Deletes the given tutorial.
     * The tutorial must exist in the address book.
     */
    void deleteTutorial(Tutorial tutorial);

    /**
     * Creates/Adds the given tutorial.
     * {@code tutorial} must not already exist in the address book.
     */
    void createTutorial(Tutorial tutorial);

    /** Returns an unmodifiable view of the filtered tutorial list */
    ObservableList<Tutorial> getFilteredTutorialList();

    /**
     * Updates the filter of the filtered tutorial list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTutorialList(Predicate<Tutorial> predicate);

    /** Returns an unmodifiable view of the entire tutorial list */
    ObservableList<Tutorial> getTutorialList();



    //// Participation-related methods ////

    boolean hasParticipation(Participation participation);

    /**
     * Adds the given participation.
     * {@code participation} must not already exist in the address book.
     */
    public void addParticipation(Participation participation);

    /**
     * Deletes the given participation.
     * The participation must exist in the address book.
     */
    void deleteParticipation(Participation target);

    /**
     * Replaces the given participation {@code target} with {@code editedParticipation}.
     * {@code target} must exist in the address book.
     * The participation identity of {@code editedParticipation} must not be the same as another
     * existing participation in the address book.
     */
    void setParticipation(Participation target, Participation editedParticipation);

    /** Returns an unmodifiable view of the participation list */
    ObservableList<Participation> getParticipationList();

}
