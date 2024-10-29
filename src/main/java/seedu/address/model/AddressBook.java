package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.UniqueStudentList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level.
 * Duplicates are not allowed (by .isSamePerson, .isSameGroup, and .isSameTask comparison).
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueStudentList students;
    private final UniqueGroupList groups;
    private final UniqueTaskList tasks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        groups = new UniqueGroupList();
        tasks = new UniqueTaskList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons, Groups, and Tasks in the {@code toBeCopied}.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setPersons(students);
    }

    /**
     * Replaces the contents of the student list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    public void setStatus() {
        for (Group g: groups) {
            g.setTaskStatus();
        }
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
        setGroups(newData.getGroupList());
        setTasks(newData.getTaskList());
    }

    /**
     * Creates a defensive copy of the T_Assistant
     * @param newData  The data to be copied over.
     * @return         Returns the new addressbook
     */
    public AddressBook duplicateCopy(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        AddressBook newAddressBook = new AddressBook();
        for (Student student: newData.getStudentList()) {
            Student newStudent = new Student(student);
            newAddressBook.students.add(newStudent);
        }
        for (Group group: newData.getGroupList()) {
            Group newGroup = new Group(group);
            newAddressBook.groups.add(newGroup);
        }
        for (Task task: newData.getTaskList()) {
            Task newTask = new Task(task);
            newAddressBook.tasks.add(newTask);
        }
        return newAddressBook;
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the address
     * book.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setPerson(target, editedStudent);
    }

    /**
     * Returns true if a student with the same identity as {@code student} exists in the group
     * with the same identity as {@code group}.
     */
    public boolean hasStudentInGroup(Student student, Group group) {
        requireNonNull(student);
        requireNonNull(group);
        return group.getStudents().contains(student);
    }

    /**
     * Adds {@code student} to {@code group}.
     * {@code student} and {@code group} must exist in the address book.
     */
    public void addStudentToGroup(Student student, Group group) {
        requireNonNull(student);
        requireNonNull(group);
        group.add(student.setStudentGroup(group.getGroupName()));
        students.setPerson(student, student.setStudentGroup(group.getGroupName()));
    }

    /**
     * Deletes the student {@code student} from the given group {@code group}.
     */
    public void deleteStudentFromGroup(Group group, Student student) {
        requireNonNull(group);
        requireNonNull(student);
        group.delete(student);
        students.setPerson(student, student.removeGroup());
    }

    public Student getStudentByNumber(StudentNumber studentNumber) {
        return students.getStudentByNumber(studentNumber);
    }

    //// group-level operations

    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }

    /**
     * Returns true if a group with the same group name as {@code groupName} exists in the address book.
     */
    public boolean containsGroupName(GroupName groupName) {
        requireNonNull(groupName);
        return groups.containsGroupWithName(groupName);
    }

    /**
     * Adds a group to the address book.
     * The group must not already exist in the address book.
     */
    public void addGroup(Group p) {
        groups.add(p);
    }

    public void removeGroup(Group groupToBeDeleted) {
        groups.remove(groupToBeDeleted);
    }

    /**
     * Replaces the given group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the address
     * book.
     */
    public void setGroup(Group target, Group editedGroup) {
        requireNonNull(editedGroup);

        groups.setGroup(target, editedGroup);
    }

    public Group findGroupByName(GroupName groupName) {
        return groups.findGroupByName(groupName);
    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the group
     * with the same identity as {@code group}.
     */
    public boolean hasTaskInGroup(Task task, Group group) {
        requireNonNull(task);
        requireNonNull(group);
        return group.getTasks().contains(task);
    }

    /**
     * Adds {@code task} to {@code group}.
     * {@code group} must exist in the address book.
     */
    public void addTaskToGroup(Task task, Group group) {
        group.addTask(task);
    }

    /**
     * Adds {@code task} to {@code AddressBook}.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Increases the number of groups with {@code task} by 1.
     */
    public void incrementTask(Task task) {
        tasks.forEach(x -> {
            if (x.isSameTask(task)) {
                x.increaseGroupWithTask();
            }
        });
    }

    /**
     * Decreases the number of groups with {@code task} by 1.
     */
    public void decrementTask(Task task) {
        tasks.forEach(x -> {
            if (x.isSameTask(task)) {
                x.decreaseGroupWithTask();
            }
        });

        Task toDelete = null;
        for (Task t: tasks) {
            if (t.getGroupsWithTask() == 0) {
                toDelete = t;
            }
        }
        if (toDelete != null) {
            tasks.remove(toDelete);
        }
    }

    /**
     * Returns true if the {@code task} currently exists in the address book.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Deletes the {@code task} from the address book.
     */
    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Deletes {@code task} from {@code group}.
     */
    public void deleteTaskFromGroup(Task task, Group group) {
        group.deleteTask(task);
    }

    /**
     * Replaces the task {@code target} in {@code group} with {@code editedTask}.
     */
    public void setTask(Task target, Task editedTask, Group group) {
        requireNonNull(target);
        requireNonNull(editedTask);
        requireNonNull(group);
        tasks.setTask(target, editedTask);
        group.setTask(target, editedTask);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("students", students)
            .toString();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return students.equals(otherAddressBook.students)
                && groups.equals(otherAddressBook.groups)
                && tasks.equals(otherAddressBook.tasks);
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
