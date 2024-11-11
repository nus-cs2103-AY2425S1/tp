package keycontacts.model;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import keycontacts.commons.core.GuiSettings;
import keycontacts.commons.core.LogsCenter;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.student.Group;
import keycontacts.model.student.Student;

/**
 * Represents the in-memory model of the student directory data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedStudentDirectory studentDirectory;
    private final UserPrefs userPrefs;
    private final ObservableList<Student> studentList;
    private final ObservableList<Student> unfilteredStudentList;
    private final FilteredList<Student> filteredStudents;
    private final SortedList<Student> sortedFilteredStudents;

    /**
     * Initializes a ModelManager with the given studentDirectory and userPrefs.
     */
    public ModelManager(ReadOnlyStudentDirectory studentDirectory, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(studentDirectory, userPrefs);

        logger.fine("Initializing with student directory: " + studentDirectory + " and user prefs " + userPrefs);

        this.studentDirectory = new VersionedStudentDirectory(studentDirectory);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.studentDirectory.getStudentList());
        sortedFilteredStudents = new SortedList<>(filteredStudents);

        studentList = sortedFilteredStudents;
        unfilteredStudentList = this.studentDirectory.getStudentList();
    }

    public ModelManager() {
        this(new StudentDirectory(), new UserPrefs());
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
    public Path getStudentDirectoryFilePath() {
        return userPrefs.getStudentDirectoryFilePath();
    }

    @Override
    public void setStudentDirectoryFilePath(Path studentDirectoryFilePath) {
        requireNonNull(studentDirectoryFilePath);
        userPrefs.setStudentDirectoryFilePath(studentDirectoryFilePath);
    }

    //=========== StudentDirectory ================================================================================

    @Override
    public void setStudentDirectory(ReadOnlyStudentDirectory newStudentDirectory) {
        this.studentDirectory.resetData(newStudentDirectory);
    }

    @Override
    public ReadOnlyStudentDirectory getStudentDirectory() {
        return studentDirectory;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return studentDirectory.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        studentDirectory.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        studentDirectory.addStudent(student);
        filterStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        studentDirectory.setStudent(target, editedStudent);
    }

    @Override
    public void undoStudentDirectory() {
        studentDirectory.undo();
    }

    @Override
    public void redoStudentDirectory() {
        studentDirectory.redo();
    }

    @Override
    public boolean canUndoStudentDirectory() {
        return studentDirectory.canUndo();
    }
    @Override
    public boolean canRedoStudentDirectory() {
        return studentDirectory.canRedo();
    }

    @Override
    public void commitStudentDirectory() {
        studentDirectory.commit();
    }

    @Override
    public Set<Lesson> getClashingLessons() {
        return studentDirectory.getClashingLessons();
    }

    @Override
    public ArrayList<Student> getStudentsInGroup(Group targetGroup) {
        requireNonNull(targetGroup);

        return studentDirectory.getStudentsInGroup(targetGroup);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the
     * internal list of
     * {@code versionedStudentDirectory}
     */
    @Override
    public ObservableList<Student> getStudentList() {
        return studentList;
    }

    @Override
    public ObservableList<Student> getUnfilteredStudentList() {
        return unfilteredStudentList;
    }

    @Override
    public void filterStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void sortStudentList(Comparator<Student> comparator) {
        sortedFilteredStudents.setComparator(comparator);
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
        return studentDirectory.equals(otherModelManager.studentDirectory)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredStudents.equals(otherModelManager.filteredStudents);
    }

}
