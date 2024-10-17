package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.project.ProjectId;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {
    public static final AssignmentId DEFAULT_ASSIGNMENT_ID = new AssignmentId("1");
    public static final ProjectId DEFAULT_PROJECT_ID = TypicalProjects.ALPHA.getId();
    public static final EmployeeId DEFAULT_EMPLOYEE_ID = TypicalPersons.ALICE.getEmployeeId();

    private AssignmentId assignmentId;
    private ProjectId projectId;
    private EmployeeId employeeId;

    /**
     * Creates a {@code AssignmentBuilder} with the default details.
     */
    public AssignmentBuilder() {
        assignmentId = DEFAULT_ASSIGNMENT_ID;
        projectId = DEFAULT_PROJECT_ID;
        employeeId = DEFAULT_EMPLOYEE_ID;
    }

    /**
     * Initializes the AssignmentBuilder with the data of {@code assignmentToCopy}.
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        assignmentId = assignmentToCopy.getAssignmentId();
        projectId = assignmentToCopy.getProjectId();
        employeeId = assignmentToCopy.getEmployeeId();
    }

    /**
     * Sets the {@code assignmentId} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withAssignmentId(String assignmentId) {
        this.assignmentId = new AssignmentId(assignmentId);
        return this;
    }

    /**
     * Sets the {@code projectId} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withProjectId(String projectId) {
        this.projectId = new ProjectId(projectId);
        return this;
    }

    /**
     * Sets the {@code person} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withEmployeeId(String employeeId) {
        this.employeeId = new EmployeeId(employeeId);
        return this;
    }

    public Assignment build() {
        return new Assignment(assignmentId, projectId, employeeId);
    }
}
