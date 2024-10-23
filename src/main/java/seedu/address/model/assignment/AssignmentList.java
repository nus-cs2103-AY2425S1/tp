package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.model.student.Student;

/**
 * Represents a list of assignments.
 */
public class AssignmentList {
    private final ArrayList<Assignment> assignments;

    /**
     * Constructs an empty AssignmentList.
     */
    public AssignmentList() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Constructs an AssignmentList with specified assignments.
     *
     * @param assignments The assignments in the list.
     */
    public AssignmentList(ArrayList<Assignment> assignments) {
        requireNonNull(assignments);

        //to avoid duplicate assignments in arraylist
        HashSet<Assignment> uniqueAssignments = new HashSet<>();
        for (Assignment assignment : assignments) {
            requireNonNull(assignment);
            if (uniqueAssignments.contains(assignment)) {
                throw new DuplicateAssignmentException();
            }
            uniqueAssignments.add(assignment);
        }
        this.assignments = assignments;
    }

    /**
     * Adds an assignment to the list.
     *
     * @param assignment The assignment to be added.
     */
    public void addAssignment(Assignment assignment) {
        if (hasAssignment(assignment)) {
            throw new DuplicateAssignmentException();
        }
        assignments.add(assignment);
    }

    public ArrayList<Assignment> getAssignments() {
        return this.assignments;
    }

    /**
     * Checks if the list contains a specific assignment.
     *
     * @param assignment The assignment to check for.
     * @return True if the list contains the assignment, false otherwise.
     */
    public boolean hasAssignment(Assignment assignment) {
        return this.assignments.contains(assignment);
    }

    /**
     * Deletes the specified assignment from the list.
     * The assignment must exist in the list.
     *
     * @param assignment The assignment to be deleted.
     * @throws AssignmentNotFoundException if the assignment does not exist in the list.
     */
    public void deleteAssignment(Assignment assignment) {
        if (!hasAssignment(assignment)) {
            throw new AssignmentNotFoundException();
        }
        assignments.remove(assignment);
    }

    /**
     * Returns completion statuses of all students for the specified assignment.
     * @param assignment Assignment to be checked.
     * @param studentList Current list of students.
     * @return String representing statuses for the assignment.
     * @throws AssignmentNotFoundException If the assignment is not found.
     */
    public String getStatus(Assignment assignment, ObservableList<Student> studentList)
            throws AssignmentNotFoundException {
        Assignment targetAssignment = getAssignment(assignment);
        StringBuilder completedList = new StringBuilder("Students who have completed: \n");
        StringBuilder uncompletedList = new StringBuilder("Students who have not completed: \n");
        for (Student student : studentList) {
            if (targetAssignment.getStatus(student.getStudentId().value)) {
                completedList.append(student.getName()).append(", ");
            } else {
                uncompletedList.append(student.getName()).append(", ");
            }
        }
        String completedString = completedList.substring(0, Math.max(30, completedList.length() - 2));
        String uncompletedString = uncompletedList.substring(0, Math.max(34, uncompletedList.length() - 2));
        return targetAssignment.toString() + "\n" + completedString + "\n" + uncompletedString;
    }

    /**
     * Sets the status of assignment of the given student to a specified boolean value.
     * @param assignment The assignment to be marked or unmarked.
     * @param targetStudent The target student that completes or not completes the assignment.
     * @param newStatus New boolean value of the status.
     * @throws AssignmentNotFoundException If the assignment is not found.
     */
    public void setStatus(Assignment assignment, Student targetStudent, boolean newStatus)
            throws AssignmentNotFoundException {
        Assignment targetAssignment = getAssignment(assignment);
        String studentId = targetStudent.getStudentId().value;
        targetAssignment.markStatus(studentId, newStatus);
    }

    /**
     * Retrieves assignment within the list with given title.
     *
     * @param assignment The assignment used to contain the title.
     * @return The assignment of given title within the list.
     * @throws AssignmentNotFoundException If the assignment is not found.
     */
    private Assignment getAssignment(Assignment assignment) throws AssignmentNotFoundException {
        return assignments.stream()
                .filter(assignment::equals)
                .findFirst()
                .orElseThrow(AssignmentNotFoundException::new);
    }

    /**
     * Resets the existing data of this {@code AssignmentList} with {@code newData}.
     */
    public void resetData(AssignmentList newData) {
        requireNonNull(newData);
        assignments.clear();
        assignments.addAll(newData.assignments);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < assignments.size(); i++) {
            sb.append(i + 1).append(". ").append(assignments.get(i).toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AssignmentList)) {
            return false;
        }
        AssignmentList otherList = (AssignmentList) other;
        return this.assignments.equals(otherList.assignments);
    }
}
