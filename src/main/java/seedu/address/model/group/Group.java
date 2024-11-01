package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.group.exceptions.ExceedGroupSizeException;
import seedu.address.model.student.Student;
import seedu.address.model.task.Task;

/**
 * Represents a Group in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Group {

    private static final int MAXIMUM_STUDENTS_IN_GROUP = 5;
    private final GroupName groupName;
    private final Set<Student> students = new HashSet<>();
    private final Set<Task> tasks = new HashSet<>();

    /**
     * Creates a Group with {@code groupName}, {@code students}, and {@code tasks}.
     * Every field must be present and not null.
     */
    public Group(GroupName groupName, Set<Student> students, Set<Task> tasks) {
        requireAllNonNull(groupName, students);
        this.groupName = groupName;
        this.students.addAll(students);
        this.tasks.addAll(tasks);
    }

    /**
     * Creates a Group with {@code groupName}.
     * Every field must be present and not null.
     */
    public Group(GroupName groupName) {
        requireAllNonNull(groupName);
        this.groupName = groupName;
    }

    /**
     * Creates a defensive copy of the group
     * @param otherGroup  The group whose values are to be copied
     */
    public Group(Group otherGroup) {
        requireNonNull(otherGroup);
        this.groupName = otherGroup.groupName;
        for (Task task: otherGroup.getTasks()) {
            Task newTask = new Task(task);
            tasks.add(newTask);
        }
        for (Student student: otherGroup.getStudents()) {
            Student newStudent = new Student(student);
            this.students.add(newStudent);
        }
    }

    public GroupName getGroupName() {
        return this.groupName;
    }

    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    public boolean hasStudents() {
        return !students.isEmpty();
    }

    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks);
    }

    /**
     * Adds {@code student} into {@code Group}.
     * There can be a maximum of 5 students in each group.
     *
     * @param student The student to be added.
     */
    public void add(Student student) {
        if (students.size() < MAXIMUM_STUDENTS_IN_GROUP) {
            students.add(student);
        } else {
            throw new ExceedGroupSizeException();
        }
    }

    /**
     * Adds {@code task} into {@code Group}.
     * There can be a maximum of 5 students in each group.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes {@code task} from {@code Group}.
     *
     * @param task The task to be deleted.
     */
    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Replaces {@code target} with {@code editedTask}.
     *
     * @param target The task to be replaced.
     * @param editedTask The task to replace with.
     */
    public void setTask(Task target, Task editedTask) {
        deleteTask(target);
        addTask(editedTask);
    }

    public void setTaskStatus() {
        for (Task t: tasks) {
            t.setStatus();
        }
    }

    /**
     * Deletes {@code student} from {@code Group}.
     *
     * @param student The student to be deleted.
     */
    public void delete(Student student) {
        students.remove(student);
    }

    /**
     * Returns true if both groups have the same name.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
            && otherGroup.getGroupName().equals(getGroupName());
    }
    /**
     * Returns true if the group has given task.
     */
    public boolean hasTask(Task task) {
        return tasks.contains(task);
    }
    /**
     * Returns true if both group have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherStudent = (Group) other;
        return groupName.equals(otherStudent.groupName)
            && students.equals(otherStudent.students);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(groupName, students);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("groupname", groupName)
            .add("students", students)
            .toString();
    }
}
