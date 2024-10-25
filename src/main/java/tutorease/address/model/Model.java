package tutorease.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tutorease.address.commons.core.GuiSettings;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Lesson> PREDICATE_SHOW_ALL_LESSONS = unused -> true;
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
    Path getTutorEaseFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setTutorEaseFilePath(Path tutorEaseFilePath);

    /**
     * Replaces address book data with the data in {@code TutorEase}.
     */
    void setTutorEase(ReadOnlyTutorEase tutorEase);

    /** Returns the TutorEase */
    ReadOnlyTutorEase getTutorEase();

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
     * Returns the lesson schedule.
     *
     * @return The lesson schedule.
     */
    LessonSchedule getLessonSchedule();

    ObservableList<Lesson> getFilteredLessonList();

    void updateFilteredLessonList(Predicate<Lesson> predicate);

    /**
     * Adds the given lesson.
     *
     * @param lesson The lesson to add.
     */
    void addLesson(Lesson lesson);

    /**
     * Returns true if a lesson with the same identity as {@code lesson} overlaps in the lesson schedule.
     *
     * @param lesson The lesson we are looking for in the lesson schedule.
     * @return A boolean indicating if the lesson is in the lessons schedule.
     */
    boolean hasLessons(Lesson lesson);

    /**
     * Deletes the given lesson.
     *
     * @param lesson The lesson to delete.
     */
    void deleteLesson(Lesson lesson);

    /**
     * Returns lesson at the specified index.
     *
     * @param index The index of the lesson to get.
     * @return The lesson at the specified index.
     */
    Lesson getLesson(int index);

    /**
     * Returns size of lesson schedule.
     *
     * @return The size of the lesson schedule
     */
    int getLessonScheduleSize();

    /**
     * Deletes all the lessons of a particular student
     *
     * @param student The student whose lessons we are deleting
     */
    void deleteStudentLesson(Person student);
}
