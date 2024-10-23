package seedu.edulog.model;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.edulog.commons.core.GuiSettings;
import seedu.edulog.commons.core.LogsCenter;
import seedu.edulog.model.calendar.EdulogCalendar;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.student.Student;

/**
 * Represents the in-memory model of all data, inclusive of both the student and lesson list.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EduLog eduLog;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Lesson> lessons;

    /**
     * Initializes a ModelManager with the given eduLog and userPrefs.
     */
    public ModelManager(ReadOnlyEduLog eduLog, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(eduLog, userPrefs);

        logger.fine("Initializing with edulog storage: " + eduLog + " and user prefs " + userPrefs);

        this.eduLog = new EduLog(eduLog);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredStudents = new FilteredList<>(this.eduLog.getStudentList());
        lessons = new FilteredList<>(this.eduLog.getLessonList());
    }

    public ModelManager() {
        this(new EduLog(), new UserPrefs());
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
    public Path getEduLogFilePath() {
        return userPrefs.getEduLogFilePath();
    }

    @Override
    public void setEduLogFilePath(Path eduLogFilePath) {
        requireNonNull(eduLogFilePath);
        userPrefs.setEduLogFilePath(eduLogFilePath);
    }

    //=========== EduLog ================================================================================

    @Override
    public void setEduLog(ReadOnlyEduLog eduLog) {
        this.eduLog.resetData(eduLog);
    }

    @Override
    public ReadOnlyEduLog getEduLog() {
        return eduLog;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return eduLog.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        eduLog.removeStudent(target);
    }

    /**
     * Marks the given student as present.
     * The student must exist in the address book.
     *
     * @param target the student to mark as present.
     */
    @Override
    public void markStudent(Student target) {
        requireNonNull(target);
        eduLog.markStudent(target);
    }

    /**
     * Mark all students
     */
    @Override
    public void markAllStudents() {
        eduLog.markAllStudents();
    }

    /**
     * Marks the given student as absent.
     * The student must exist in the address book.
     *
     * @param target the student to mark as absent.
     */
    @Override
    public void unmarkStudent(Student target) {
        requireNonNull(target);
        eduLog.unmarkStudent(target);
    }

    /**
     * Unmark all students
     */
    @Override
    public void unmarkAllStudents() {
        eduLog.unmarkAllStudents();
    }

    @Override
    public void addStudent(Student student) {
        eduLog.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        eduLog.setStudent(target, editedStudent);
    }

    //=========== EdulogCalendar ================================================================================

    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return eduLog.hasLesson(lesson);
    }

    @Override
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        eduLog.addLesson(lesson);
    }

    @Override
    public Lesson findLesson(String description) {
        requireNonNull(description);
        return eduLog.getEdulogCalendar().findLesson(description);
    }

    @Override
    public boolean checkTimeslot(Lesson lesson) {
        requireNonNull(lesson);
        return eduLog.getEdulogCalendar().checkTimeslot(lesson);
    }

    @Override
    public void removeLesson(Lesson lesson) {
        requireNonNull(lesson);
        eduLog.getEdulogCalendar().removeLesson(lesson);
    }

    public EdulogCalendar getEdulogCalendar() {
        return eduLog.getEdulogCalendar();
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedEduLog}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Lesson} backed by the internal list of
     * {@code ?? - fill}
     */
    @Override
    public ObservableList<Lesson> getLessonList() {
        return lessons;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
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
        return eduLog.equals(otherModelManager.eduLog)
            && userPrefs.equals(otherModelManager.userPrefs)
            && filteredStudents.equals(otherModelManager.filteredStudents);
    }

}
