package seedu.hiredfiredpro.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.hiredfiredpro.commons.core.GuiSettings;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.Person;

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
     * Returns true if the person exists in the hiredfiredpro.
     */
    Person findPersonByNameAndJob(Name name, Job job);

    /**
     * Returns an unmodifiable view of the sorted and filtered person list
     */
    ObservableList<Person> getSortedFilteredPersonList();

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
     * Returns the user prefs' hiredfiredpro file path.
     */
    Path getHiredFiredProFilePath();

    /**
     * Sets the user prefs' hiredfiredpro file path.
     */
    void setHiredFiredProFilePath(Path hiredFiredProPath);

    /**
     * Replaces hiredfiredpro data with the data in {@code hiredfiredpr}.
     */
    void setHiredFiredPro(ReadOnlyHiredFiredPro hiredFiredPro);

    /** Replaces the current list of persons with the given list. */
    void setPersons(List<Person> persons);

    /** Returns the hiredfiredpr */
    ReadOnlyHiredFiredPro getHiredFiredPro();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the hiredfiredpro.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the hiredfiredpro.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the hiredfiredpro.
     */
    void addPerson(Person person);

    void updateSortedPersonList(Comparator<Person> comparator);

    ObservableList<Person> getSortedPersonList();

    void markAsHired(Person person);

    boolean isJobPresent(Job job);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the hiredfiredpro.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the hiredfiredpro.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
