package seedu.academyassist.model;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.academyassist.commons.core.GuiSettings;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;

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
     * Returns the user prefs' Academy Assist file path.
     */
    Path getAcademyAssistFilePath();

    /**
     * Sets the user prefs' Academy Assist book file path.
     */
    void setAcademyAssistFilePath(Path academyAssistFilePath);

    /**
     * Replaces Academy Assist data with the data in {@code academyassist}.
     */
    void setAcademyAssist(ReadOnlyAcademyAssist academyAssist);

    /** Returns the Academy Assist */
    ReadOnlyAcademyAssist getAcademyAssist();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the Academy Assist management
     * system.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with this {@code ic} exists in the Academy Assist management system.
     */
    boolean hasPersonWithIc(Ic ic);

    /**
     * Returns true if a person with this {@code student id} exists in the Academy Assist management system.
     */
    boolean hasPersonWithStudentId(StudentId studentId);

    /**
     * Returns true if {@code Person} already is taking any of {@code subjects}
     */
    boolean personDuplicateClass(Set<Subject> subjects, Person student);

    /**
     * Adds {@code subject(s)} to {@code person} in Academy Assist management system.
     */
    void addSubjectsToPerson(Set<Subject> subjects, Person person);

    /**
     * Deletes the given person.
     * The person must exist in the academy assist management system.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the academy assist management system.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the academy assist management system.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the
     * academy assist management system.
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
     * Sorts the academy assist management system book managed by model, based on alphabetical order of names of
     * persons inside it.
     */
    void sortAcademyAssistByName();

    /**
     * Sorts the academy assist management system managed by model, based on alphabetical order of classes of
     * persons inside it.
     */
    void sortAcademyAssistByClass();

    /**
     * Increment student count by 1.
     */
    void incrementStudentCount();

    /**
     * Returns student count from academy assist management system.
     */
    int getStudentCount();

    /**
     * Returns person from academy assist management system with given ic.
     */
    Person getPersonWithIc(Ic ic);

    /**
     * Returns person from academy assist management system with given {@code student id}.
     */
    Person getPersonWithStudentId(StudentId studentId);

}
