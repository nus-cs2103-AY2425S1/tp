package tutorease.address.model;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tutorease.address.commons.core.GuiSettings;
import tutorease.address.commons.core.LogsCenter;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TutorEase tutorEase;
    private final UserPrefs userPrefs;
    private final LessonSchedule lessonSchedule;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Lesson> filteredLesson;

    /**
     * Initializes a ModelManager with the given tutorEase and userPrefs.
     */
    public ModelManager(ReadOnlyTutorEase tutorEase, ReadOnlyUserPrefs userPrefs, LessonSchedule lessonSchedule) {
        requireAllNonNull(tutorEase, userPrefs, lessonSchedule);

        logger.fine("Initializing with address book: " + tutorEase + " and user prefs " + userPrefs
                + " and lesson schedule " + lessonSchedule);

        this.tutorEase = new TutorEase(tutorEase);
        this.userPrefs = new UserPrefs(userPrefs);
        this.lessonSchedule = new LessonSchedule(lessonSchedule);
        filteredPersons = new FilteredList<>(this.tutorEase.getPersonList());
        filteredLesson = new FilteredList<>(this.lessonSchedule.getLessonList());
    }

    public ModelManager() {
        this(new TutorEase(), new UserPrefs(), new LessonSchedule());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTutorEaseFilePath() {
        return userPrefs.getTutorEaseFilePath();
    }

    @Override
    public void setTutorEaseFilePath(Path tutorEaseFilePath) {
        requireNonNull(tutorEaseFilePath);
        userPrefs.setTutorEaseFilePath(tutorEaseFilePath);
    }

    //=========== TutorEase ================================================================================

    @Override
    public void setTutorEase(ReadOnlyTutorEase tutorEase) {
        this.tutorEase.resetData(tutorEase);
    }

    @Override
    public ReadOnlyTutorEase getTutorEase() {
        return tutorEase;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return tutorEase.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        tutorEase.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        tutorEase.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        tutorEase.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTutorEase}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return tutorEase.equals(otherModelManager.tutorEase)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== LessonSchedule ================================================================================

    @Override
    public LessonSchedule getLessonSchedule() {
        return lessonSchedule;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Lesson} backed by the internal list of
     * {@code versionedTutorEase}
     */
    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return filteredLesson;
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        filteredLesson.setPredicate(predicate);
    }

    @Override
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessonSchedule.addLesson(lesson);
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
    }

    @Override
    public void deleteLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessonSchedule.deleteLesson(lesson);
    }

    @Override
    public Lesson getLesson(int index) {
        return lessonSchedule.getLesson(index);
    }

    @Override
    public boolean hasLessons(Lesson lesson) {
        requireNonNull(lesson);
        return lessonSchedule.hasLesson(lesson);
    }

    @Override
    public int getLessonScheduleSize() {
        return lessonSchedule.getSize();
    }

    @Override
    public void deleteStudentLesson(Person student) {
        int currentIndex = 0;
        while (currentIndex < this.getLessonScheduleSize()) {
            Lesson lesson = this.getLesson(currentIndex);
            if (student.equals(lesson.getStudent())) {
                this.deleteLesson(lesson);
            } else {
                currentIndex++;
            }
        }
    }
}
