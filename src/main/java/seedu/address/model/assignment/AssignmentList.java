package seedu.address.model.assignment;

import java.util.ArrayList;

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
        this.assignments.add(assignment);
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
}
