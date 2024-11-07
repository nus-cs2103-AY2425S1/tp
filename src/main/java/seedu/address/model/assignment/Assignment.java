package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;

/**
 * Represents a (Project) Assignment in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Assignment {
    private final AssignmentId assignmentId;
    private final Project project;
    private final Employee employee;

    /**
     * Every field must be present and not null.
     */
    public Assignment(AssignmentId assignmentId, Project project, Employee employee) {
        requireAllNonNull(project, employee);
        this.assignmentId = assignmentId;
        this.project = project;
        this.employee = employee;
    }

    public AssignmentId getAssignmentId() {
        return assignmentId;
    }

    public Project getProject() {
        return project;
    }

    public Employee getEmployee() {
        return employee;
    }

    /**
     * Returns true if both {@code Project#equals(Project)} and
     * {@code Employee#equals(Employee)} returns true.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        if (otherAssignment == null) {
            return false;
        }

        boolean isSameProject = otherAssignment.getProject().equals(project);
        boolean isSameEmployee = otherAssignment.getEmployee().equals(employee);
        return isSameEmployee && isSameProject;
    }

    /**
     * Returns true if both {@code ProjectId#equals(ProjectId)} and
     * {@code EmployeeId#equals(EmployeeId)} returns true.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(ProjectId projectId, EmployeeId employeeId) {
        if (projectId == null || employeeId == null) {
            return false;
        }

        boolean isSameProject = projectId.equals(this.project.getId());
        boolean isSameEmployee = employeeId.equals(this.employee.getEmployeeId());
        return isSameEmployee && isSameProject;
    }

    /**
     * Returns true if {@code assignmentId#equals(assignmentId)}.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(AssignmentId assignmentId) {
        if (assignmentId == null) {
            return false;
        }

        return this.assignmentId.equals(assignmentId);
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
                && project.equals(otherAssignment.project)
                && employee.equals(otherAssignment.employee);
    }
}
