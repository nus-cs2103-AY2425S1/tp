package seedu.address.model.group;

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
     * Every field must be present and not null.
     */
    public Group(GroupName groupName, Set<Student> students, Set<Task> tasks) {
        requireAllNonNull(groupName, students);
        this.groupName = groupName;
        this.students.addAll(students);
        this.tasks.addAll(tasks);
    }

    /**
     * Every field must be present and not null.
     */
    public Group(GroupName groupName) {
        requireAllNonNull(groupName);
        this.groupName = groupName;
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
     * Adds a given student into the {@code group} object.
     * There can be a maximum of 5 students in each group.
     *
     * @param student The {@code student} object to be added.
     */
    public void add(Student student) {
        if (students.size() < MAXIMUM_STUDENTS_IN_GROUP) {
            students.add(student);
        } else {
            throw new ExceedGroupSizeException();
        }
    }

    /**
     * Adds a given task into the {@code group} object.
     * There can be a maximum of 5 students in each group.
     *
     * @param task The {@code task} object to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public void setTask(Task target, Task editedTask) {
        deleteTask(target);
        addTask(editedTask);
    }

    public void delete(Student student) {
        students.remove(student);
    }

    /**
     * Returns true if both groups have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
            && otherGroup.getGroupName().equals(getGroupName());
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
