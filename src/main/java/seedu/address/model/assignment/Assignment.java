package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.EmployeeId;
import seedu.address.model.project.ProjectId;

/**
 * Represents a (Project) Assignment in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Assignment {
    private final AssignmentId assignmentId;
    private final ProjectId projectId;
    private final EmployeeId employeeId;

    /**
     * Every field must be present and not null.
     */
    public Assignment(AssignmentId assignmentId, ProjectId projectId, EmployeeId employeeId) {
        requireAllNonNull(projectId, employeeId);
        this.assignmentId = assignmentId;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }

    public AssignmentId getAssignmentId() {
        return assignmentId;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    /**
     * Returns true if both {@code ProjectId#equals(ProjectId)} and
     * {@code EmployeeId#equals(EmployeeId)} returns true.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        if (otherAssignment == null) {
            return false;
        }

        boolean isSameProject = otherAssignment.getProjectId().equals(projectId);
        boolean isSamePerson = otherAssignment.getEmployeeId().equals(employeeId);
        return isSamePerson && isSameProject;
    }

    /**
     * Returns true if both assignments have the same identity and data fields.
     * This defines a stronger notion of equality between two assignments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return assignmentId.equals(otherAssignment.assignmentId)
                && projectId.equals(otherAssignment.projectId)
                && employeeId.equals(otherAssignment.employeeId);
    }
}
