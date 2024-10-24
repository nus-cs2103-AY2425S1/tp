package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.State;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.task.Task;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;

    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Group> filteredGroups;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.addressBook.getStudentList());
        filteredGroups = new FilteredList<>(this.addressBook.getGroupList());
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setStateStudents() {
        this.userPrefs.setStateStudents();
    }

    @Override
    public void setStateGroups() {
        this.userPrefs.setStateGroups();
    }

    @Override
    public void setStateGroupTask() {
        this.userPrefs.setStateGroupTask();
    }

    @Override
    public void setStateTasks() {
        this.userPrefs.setStateTasks();
    }

    @Override
    public State getState() {
        return this.userPrefs.getState();
    }


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
    public String getMostRecentGroupTaskDisplay() {
        return userPrefs.getMostRecentGroupTaskDisplay();
    }

    @Override
    public void setMostRecentGroupTaskDisplay() {
        String stringToUpdate = userPrefs.getMostRecentGroupTaskDisplay();
        if (!stringToUpdate.equals("")) {
            updateFilteredGroupList(x -> x.getGroupName().fullName.equals(stringToUpdate));
        } else {
            updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        }
    }

    @Override
    public void setMostRecentGroupTaskDisplay(String string) {
        userPrefs.setMostRecentGroupTaskDisplay(string);
        if (!string.equals("")) {
            updateFilteredGroupList(x -> x.getGroupName().fullName.equals(string));
        } else {
            updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        }
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Student student) {
        requireNonNull(student);
        return addressBook.hasStudent(student);
    }

    @Override
    public void deletePerson(Student target) {
        addressBook.removeStudent(target);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addPerson(Student student) {
        addressBook.addStudent(student);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        addressBook.setStudent(target, editedStudent);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setTask(Task target, Task editedTask, Group group) {
        requireAllNonNull(target, editedTask, group);
        addressBook.setTask(target, editedTask, group);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return addressBook.hasGroup(group);
    }

    @Override
    public void addGroup(Group group) {
        addressBook.addGroup(group);
        if (userPrefs.getState().toString().equals(new State("Groups"))) {
            updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        }
    }

    @Override
    public void setGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);
        addressBook.setGroup(target, editedGroup);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    @Override
    public boolean hasPersonInGroup(Student student, Group group) {
        requireAllNonNull(student, group);
        return addressBook.hasStudentInGroup(student, group);
    }

    @Override
    public void addPersonToGroup(Student student, Group group) {
        requireAllNonNull(student, group);
        addressBook.addStudentToGroup(student, group);
    }

    @Override
    public Student getPersonByNumber(StudentNumber studentNumber) {
        return addressBook.getStudentByNumber(studentNumber);
    }

    @Override
    public Group getGroupByName(GroupName groupName) {
        return addressBook.findGroupByName(groupName);
    }

    @Override
    public boolean containsGroupName(GroupName groupName) {
        requireNonNull(groupName);
        return addressBook.containsGroupName(groupName);
    }

    @Override
    public void deleteStudentFromGroup(Group group, Student student) {
        requireNonNull(group);
        requireNonNull(student);
        addressBook.deleteStudentFromGroup(group, student);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void deleteGroup(Group groupToBeDeleted) {
        requireNonNull(groupToBeDeleted);
        addressBook.removeGroup(groupToBeDeleted);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void sortPersonList(Comparator<Student> comparator) {
        requireAllNonNull(comparator);
        addressBook.setStudents(filteredStudents.sorted(comparator));
    }

    @Override
    public void sortGroupList(Comparator<Group> comparator) {
        requireAllNonNull(comparator);
        addressBook.setGroups(filteredGroups.sorted(comparator));
    }

    @Override
    public void sortTaskList(Comparator<Task> comparator) {
        requireAllNonNull(comparator);
        addressBook.setTasks(filteredTasks.sorted(comparator));
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredPersonList() {
        return filteredStudents;
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
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
        return addressBook.equals(otherModelManager.addressBook)
            && userPrefs.equals(otherModelManager.userPrefs)
            && filteredStudents.equals(otherModelManager.filteredStudents)
            && filteredGroups.equals(otherModelManager.filteredGroups)
            && filteredTasks.equals(otherModelManager.filteredTasks);
    }

    @Override
    public boolean hasTaskInGroup(Task task, Group group) {
        requireAllNonNull(task, group);
        return addressBook.hasTaskInGroup(task, group);
    }

    @Override
    public void addTaskToGroup(Task task, Group group) {
        requireAllNonNull(task, group);
        addressBook.addTaskToGroup(task, group);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void addTask(Task task) {
        requireNonNull(task);
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public boolean hasTask(Task task) {
        return addressBook.hasTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        requireNonNull(task);
        addressBook.deleteTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void deleteTaskFromGroup(Task task, Group group) {
        requireNonNull(task);
        requireNonNull(group);
        addressBook.deleteTaskFromGroup(task, group);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void increaseGroupWithTask(Task task) {
        addressBook.incrementTask(task);
    }

    @Override
    public void decreaseGroupWithTask(Task task) {
        addressBook.decrementTask(task);
    }
}
