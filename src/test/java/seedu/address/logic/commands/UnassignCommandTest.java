package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ALICE_ALPHA;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalProjects.BETA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.ProjectBuilder;

public class UnassignCommandTest {

    @Test
    public void constructor_nullAssignmentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignCommand(null));
    }

    @Test
    public void constructor_nullProjectIdAndEmployeeId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UnassignCommand(null, null));
    }

    @Test
    public void execute_unassignAssignmentWithAssignmentIdByModel_addSuccessful() throws Exception {
        UnassignCommandTest.ModelStubAlwaysUnassign modelStub = new UnassignCommandTest.ModelStubAlwaysUnassign();
        Assignment validAssignment = new AssignmentBuilder().build();

        CommandResult commandResult = new UnassignCommand(validAssignment.getAssignmentId()).execute(modelStub);

        assertEquals(String.format(UnassignCommand.MESSAGE_SUCCESS_ASSIGNMENT_ID, validAssignment.getAssignmentId()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_unassignAssignmentWithProjectIdAndEmployeeIdByModel_addSuccessful() throws Exception {
        UnassignCommandTest.ModelStubAlwaysUnassign modelStub = new UnassignCommandTest.ModelStubAlwaysUnassign();
        Project validProject = new ProjectBuilder().build();
        Employee validEmployee = new EmployeeBuilder().build();

        CommandResult commandResult = new UnassignCommand(validProject.getId(),
                validEmployee.getEmployeeId()).execute(modelStub);

        assertEquals(
                String.format(UnassignCommand.MESSAGE_SUCCESS, validEmployee.getEmployeeId(), validProject.getId()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateUnassignment_throwsCommandException() {
        Assignment validAssignment = new AssignmentBuilder().build();
        UnassignCommand unassignCommand = new UnassignCommand(validAssignment.getAssignmentId());
        ModelStub modelStub = new UnassignCommandTest.ModelStubWithAssignment(validAssignment);
        assertThrows(CommandException.class,
                UnassignCommand.MESSAGE_ASSIGNMENT_NOT_FOUND, () -> {
                    unassignCommand.execute(modelStub);
                    unassignCommand.execute(modelStub);
                });
    }

    @Test
    public void execute_projectIdAndEmployeeIdNotFound_throwsCommandException() {
        Assignment validAssignment = new AssignmentBuilder().build();
        UnassignCommand unassignCommand = new UnassignCommand(validAssignment.getProject().getId(),
                validAssignment.getEmployee().getEmployeeId());
        ModelStub modelStub = new UnassignCommandTest.ModelStubWithNoAssignment();
        assertThrows(CommandException.class,
                UnassignCommand.MESSAGE_ASSIGNMENT_NOT_FOUND, () -> {
                    unassignCommand.execute(modelStub);
                });
    }

    @Test
    public void equals() {
        Assignment alphaAlice = new AssignmentBuilder().withAssignmentId("1").withProject(ALPHA)
                .withEmployee(ALICE).build();
        Assignment betaBenson = new AssignmentBuilder().withAssignmentId("2").withProject(BETA)
                .withEmployee(BENSON).build();
        UnassignCommand unassignAlphaAliceCommand = new UnassignCommand(alphaAlice.getAssignmentId());
        UnassignCommand unassignBetaBensonCommand = new UnassignCommand(betaBenson.getAssignmentId());

        // same object -> returns true
        assertTrue(unassignAlphaAliceCommand.equals(unassignAlphaAliceCommand));

        // same values -> returns true
        UnassignCommand unassignAlphaAliceCommandCopy = new UnassignCommand(alphaAlice.getAssignmentId());
        assertTrue(unassignAlphaAliceCommand.equals(unassignAlphaAliceCommandCopy));

        // different types -> returns false
        assertFalse(unassignAlphaAliceCommand.equals(1));

        // null -> returns false
        assertFalse(unassignAlphaAliceCommand.equals(null));

        // different employee -> returns false
        assertFalse(unassignAlphaAliceCommand.equals(unassignBetaBensonCommand));
    }

    @Test
    public void toStringMethod() {
        UnassignCommand unassignCommand = new UnassignCommand(ALICE_ALPHA.getAssignmentId());
        String expected = UnassignCommand.class.getCanonicalName() + "{assignmentId="
                + ALICE_ALPHA.getAssignmentId().toString() + ", projectId=null"
                + ", employeeId=null}";

        assertEquals(expected, unassignCommand.toString());
    }

    /**
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithNoAssignment extends ModelStub {
        ModelStubWithNoAssignment() {
        }

        @Override
        public boolean hasAssignment(AssignmentId assignmentId) {
            requireNonNull(assignmentId);
            return false;
        }

        @Override
        public boolean hasAssignment(ProjectId projectId, EmployeeId employeeId) {
            requireNonNull(projectId);
            requireNonNull(employeeId);
            return false;
        }
    }

    /**
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithAssignment extends ModelStub {
        private Assignment assignment;

        ModelStubWithAssignment(Assignment assignment) {
            requireNonNull(assignment);
            this.assignment = assignment;
        }

        @Override
        public boolean hasAssignment(AssignmentId assignmentId) {
            requireNonNull(assignmentId);
            return this.assignment != null && assignment.getAssignmentId().equals(assignmentId);
        }

        @Override
        public boolean hasAssignment(ProjectId projectId, EmployeeId employeeId) {
            requireNonNull(projectId);
            requireNonNull(employeeId);
            return this.assignment != null
                    && assignment.getProject().getId().equals(projectId)
                    && assignment.getEmployee().getEmployeeId().equals(employeeId);
        }

        @Override
        public void deleteAssignment(AssignmentId assignmentId) {
            requireNonNull(assignmentId);
            this.assignment = null;
        }

        @Override
        public void deleteAssignment(ProjectId projectId, EmployeeId employeeId) {
            requireNonNull(projectId);
            requireNonNull(employeeId);
            this.assignment = null;
        }
    }

    /**
     * A Model stub that always unassign assignment successfully
     */
    private class ModelStubAlwaysUnassign extends ModelStub {
        @Override
        public boolean hasAssignment(AssignmentId assignmentId) {
            requireNonNull(assignmentId);
            return true;
        }

        @Override
        public boolean hasAssignment(ProjectId projectId, EmployeeId employeeId) {
            requireNonNull(projectId);
            requireNonNull(employeeId);
            return true;
        }

        @Override
        public void deleteAssignment(AssignmentId assignmentId) {
            requireNonNull(assignmentId);
        }

        @Override
        public void deleteAssignment(ProjectId projectId, EmployeeId employeeId) {
            requireNonNull(projectId);
            requireNonNull(employeeId);
        }
    }
}
