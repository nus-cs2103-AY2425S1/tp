package seedu.address.model.assignment;

import java.util.ArrayList;

import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < assignments.size(); i++) {
            sb.append(i).append(". ").append(assignments.get(i).toString()).append("\n");
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
