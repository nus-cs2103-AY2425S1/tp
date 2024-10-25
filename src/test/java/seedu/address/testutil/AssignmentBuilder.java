package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {
    public static final AssignmentId DEFAULT_ASSIGNMENT_ID = new AssignmentId("1");
    public static final Project DEFAULT_PROJECT = TypicalProjects.ALPHA;
    public static final Employee DEFAULT_EMPLOYEE = TypicalEmployees.ALICE;

    private AssignmentId assignmentId;
    private Project project;
    private Employee employee;

    /**
     * Creates a {@code AssignmentBuilder} with the default details.
     */
    public AssignmentBuilder() {
        assignmentId = DEFAULT_ASSIGNMENT_ID;
        project = DEFAULT_PROJECT;
        employee = DEFAULT_EMPLOYEE;
    }

    /**
     * Initializes the AssignmentBuilder with the data of {@code assignmentToCopy}.
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        assignmentId = assignmentToCopy.getAssignmentId();
        project = assignmentToCopy.getProject();
        employee = assignmentToCopy.getEmployee();
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
    public AssignmentBuilder withProject(Project project) {
        this.project = new Project(project.getName(), project.getId(), project.getSkills());
        return this;
    }

    /**
     * Sets the {@code employee} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withEmployee(Employee employee) {
        this.employee = new Employee(employee.getEmployeeId(), employee.getName(), employee.getPhone(),
                employee.getEmail(),
                employee.getAddress(), employee.getTags(), employee.getSkills());
        return this;
    }

    public Assignment build() {
        return new Assignment(assignmentId, project, employee);
    }
}
