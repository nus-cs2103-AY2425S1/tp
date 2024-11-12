package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assignment.ReadOnlyPredefinedAssignmentsData;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' KonTActs file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' KonTActs file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces KonTActs data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in KonTActs.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in KonTActs.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in KonTActs.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the KonTActs.
     * The person identity of {@code editedPerson} must not be the same as another existing person in KonTActs.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns the person in KonTActs with the given name.
     *
     * @param name The name to search for
     * @return An Optional containing the matched person if that person is found, otherwise an empty Optional
     */
    Optional<Person> getPerson(Name name);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filtered person list to be sorted by the given {@code comparator}.
     * Ordering is set to default if {@code null} is the argument.
     */
    void updateSortedPersonList(Comparator<Person> comparator);

    /**
     * Returns true if an assignment with the name is present.
     */
    boolean hasAssignment(String name);

    /**
     * Returns the maximum score for a specified assignment.
     *
     * @param assignment The name of the assignment to retrieve the maximum score for.
     * @return The maximum score for the specified assignment.
     */
    float getMaxScore(String assignment);

    /**
     * Retrieves the exact name of a specified assignment.
     *
     * @param name The name to retrieve.
     * @return The assignment name as a {@code String}.
     */
    String getAssignmentName(String name);


    /**
     * Checks if a person with the specified name exists in KonTActs.
     *
     * @param name The {@code Name} of the person to check.
     * @return {@code true} if a person with the specified name exists, {@code false} otherwise.
     */
    boolean hasName(Name name);

    /**
     * Retrieves the predefined assignments data as a read-only data object.
     *
     * @return A {@code ReadOnlyPredefinedAssignmentsData} object containing predefined assignments.
     */
    ReadOnlyPredefinedAssignmentsData getPredefinedAssignments();

    /**
     * Returns {@code Github} detials of the specified {@name}
     */
    Github getGitHubUsername(Name name);

    /**
     * Replaces all persons in the predefined assignments data with a new list.
     *
     * @param newPersons A list of {@code Person} objects to replace the existing persons.
     */
    void replaceAllPersons(List<Person> newPersons);

}
