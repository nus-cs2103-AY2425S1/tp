package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.State;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.task.Task;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Student> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

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

    String getMostRecentGroupTaskDisplay();

    void setMostRecentGroupTaskDisplay();

    void setMostRecentGroupTaskDisplay(String string);

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

    /**
     * Returns the AddressBook as a {@code ReadOnlyAddressBook}.
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasPerson(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the address book.
     */
    void deletePerson(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addPerson(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not
     * be the same as another existing student in the address book.
     */
    void setPerson(Student target, Student editedStudent);

    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    boolean hasGroup(Group group);

    /**
     * Adds the given group.
     * {@code group} must not already exist in the address book.
     */
    void addGroup(Group group);

    /**
     * Replaces the given group {@code target} with {@code editedGroup}.
     * {@code target} must exist in the address book.
     * The group identity of {@code editedStudent} must not
     * be the same as another existing group in the address book.
     */
    void setGroup(Group target, Group editedGroup);

    /**
     * Adds {@code student} to {@code group}.
     * {@code student} and {@code group} must exist in the address book and the student
     * shouldn't already be in the group.
     */
    void addPersonToGroup(Student student, Group group);

    /**
     * Returns true if a student with the same identity as {@code student} exists in the group
     * with the same identity as {@code group}.
     */
    boolean hasPersonInGroup(Student student, Group group);

    /**
     * Returns true if a task with the same identity as {@code task} exists in the group
     * with the same identity as {@code task}.
     */
    boolean hasTaskInGroup(Task task, Group group);

    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    boolean hasTask(Task task);

    /**
     * Replaces the given task {@code target} with {@code editedTask} in {@code group}.
     * {@code target} must exist in the address book.
     */
    void setTask(Task target, Task editedTask, Group group);

    /**
     * Returns a {@code Student} with the student number {@code studentNumber}.
     * There must exist such a {@code Student} in the address book.
     */
    Student getPersonByNumber(StudentNumber studentNumber);

    /**
     * Returns a {@code Group} with the group name {@code groupName}.
     * There must exist such a {@code Group} in the address book.
     */
    Group getGroupByName(GroupName groupName);

    /**
     * Returns an unmodifiable view of the filtered student list.
     */
    ObservableList<Student> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered group list.
     */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Returns an unmodifiable view of the filtered task list.
     */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns a sorted student list based on the given {@code comparator}.
     *
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortPersonList(Comparator<Student> comparator);

    /**
     * Returns a sorted group list based on the given {@code comparator}.
     *
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortGroupList(Comparator<Group> comparator);

    /**
     * Returns a sorted task list based on the given {@code comparator}.
     *
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortTaskList(Comparator<Task> comparator);

    void setStateStudents();

    void setStateGroups();

    void setStateGroupTask();

    void setStateTasks();

    State getState();

    /**
     * Returns true if group with {@code groupName} exists in the address book.
     */
    boolean containsGroupName(GroupName groupName);

    /**
     * Deletes {@code student} from {@code group}.
     */
    void deleteStudentFromGroup(Group group, Student student);

    /**
     * Deletes {@code groupToBeDeleted} from the address book.
     * The students in {@code groupToBeDeleted} will be removed from that group.
     */
    void deleteGroup(Group groupToBeDeleted);

    /**
     * Adds {@code task} to {@code group}.
     */
    void addTaskToGroup(Task task, Group group);

    /**
     * Adds {@code task} to the address book.
     */
    void addTask(Task task);

    /**
     * Deletes {@code task} from {@group}.
     */
    void deleteTaskFromGroup(Task task, Group group);

    /**
     * Deletes {@code task} from the address book.
     */
    void deleteTask(Task task);

    /**
     * Increments the number of groups with {@code task} by 1.
     */
    void increaseGroupWithTask(Task task);

    /**
     * Decrements the number of groups with {@code task} by 1.
     */
    void decreaseGroupWithTask(Task task);
}
