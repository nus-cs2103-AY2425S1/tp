package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.ProjectId;

/**
 * A list of assignments that enforces uniqueness between its elements and does
 * not allow nulls.
 *
 * A assignments is considered unique by comparing using
 * {@code Assignment#isSameAssignment(Assignment)}. As such, adding and updating
 * of assignments uses Assignment#isSameAssignment(Assignment) for equality so
 * as to ensure that the assignment being added or updated is unique in terms of
 * identity in the UniqueAssignmentList. However, the removal of a assignment
 * uses Assignment#equals(Object) so as to ensure that the assignment with
 * exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Assignment#isSameAssignment(Assignment)
 */
public class UniqueAssignmentList implements Iterable<Assignment> {

    private final ObservableList<Assignment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Assignment> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent assignment as the given
     * argument.
     */
    public boolean contains(Assignment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAssignment);
    }

    /**
     * Returns true if the list contains an equivalent assignment as the given
     * argument.
     */
    public boolean contains(AssignmentId toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(assignment -> assignment.getAssignmentId().equals(toCheck));
    }

    /**
     * Returns true if the list contains an equivalent assignment as the given
     * argument.
     */
    public boolean contains(ProjectId projectToCheck, EmployeeId employeeToCheck) {
        requireNonNull(projectToCheck);
        requireAllNonNull(employeeToCheck);
        return internalList.stream().anyMatch(assignment ->
                assignment.isSameAssignment(projectToCheck, employeeToCheck));
    }


    /**
     * Adds a assignment to the list.
     * The assignment must not already exist in the list.
     */
    public void add(Assignment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAssignmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the assignment {@code target} in the list with
     * {@code editedAssignment}.
     * {@code target} must exist in the list.
     * The assignment identity of {@code editedAssignment} must not be the same as
     * another existing assignment in the list.
     */
    public void setAssignment(Assignment target, Assignment editedAssignment) {
        requireAllNonNull(target, editedAssignment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AssignmentNotFoundException();
        }

        if (!target.isSameAssignment(editedAssignment) && contains(editedAssignment)) {
            throw new DuplicateAssignmentException();
        }

        internalList.set(index, editedAssignment);
    }

    /**
     * Return assignment with matching {@code assignmentId}
     */
    public Assignment getAssignment(AssignmentId assignmentId) {
        requireNonNull(assignmentId);
        Assignment assignment = internalList.stream()
                .filter(a -> a.isSameAssignment(assignmentId))
                .findFirst().orElse(null);
        if (assignment == null) {
            throw new AssignmentNotFoundException();
        }
        return assignment;
    }

    /**
     * Return assignment with matching {@code projectId} and {@code employeeId}
     */
    public Assignment getAssignment(ProjectId projectId, EmployeeId employeeId) {
        requireNonNull(projectId);
        requireNonNull(employeeId);
        Assignment assignment = internalList
                .stream()
                .filter(a -> a.isSameAssignment(projectId, employeeId))
                .findFirst().orElse(null);
        if (assignment == null) {
            throw new AssignmentNotFoundException();
        }
        return assignment;
    }

    /**
     * Removes the equivalent assignment from the list.
     * The assignment must exist in the list.
     */
    public void remove(Assignment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AssignmentNotFoundException();
        }
    }

    /**
     * Removes the equivalent assignment from the list.
     * The assignment must exist in the list.
     */
    public void remove(AssignmentId toRemove) {
        requireNonNull(toRemove);
        if (!internalList
                .remove(internalList
                        .stream()
                        .filter(assignment -> assignment
                                .isSameAssignment(toRemove))
                        .findFirst()
                        .orElse(null))) {
            throw new AssignmentNotFoundException();
        }
    }

    /**
     * Removes the equivalent assignment from the list.
     * The assignment must exist in the list.
     */
    public void remove(ProjectId projectId, EmployeeId employeeId) {
        requireNonNull(projectId);
        requireAllNonNull(employeeId);
        if (!internalList.remove(internalList.stream().filter(assignment ->
                assignment.isSameAssignment(projectId, employeeId))
                .findFirst().orElse(null))) {
            throw new AssignmentNotFoundException();
        }
    }

    public void setAssignments(UniqueAssignmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code assignments}.
     * {@code assignments} must not contain duplicate assignments.
     */
    public void setAssignments(List<Assignment> assignments) {
        requireAllNonNull(assignments);
        if (!assignmentsAreUnique(assignments)) {
            throw new DuplicateAssignmentException();
        }

        internalList.setAll(assignments);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Assignment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Assignment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueAssignmentList)) {
            return false;
        }

        UniqueAssignmentList otherUniqueAssignmentList = (UniqueAssignmentList) other;
        return internalList.equals(otherUniqueAssignmentList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code assignments} contains only unique assignments.
     */
    private boolean assignmentsAreUnique(List<Assignment> assignments) {
        for (int i = 0; i < assignments.size() - 1; i++) {
            for (int j = i + 1; j < assignments.size(); j++) {
                if (assignments.get(i).isSameAssignment(assignments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
