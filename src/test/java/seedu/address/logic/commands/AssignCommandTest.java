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

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.ModelStub;

public class AssignCommandTest {
    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignCommand(null));
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAssignmentAdded modelStub = new ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = new AssignmentBuilder().build();
        CommandResult commandResult = new AssignCommand(validAssignment).execute(modelStub);
        assertEquals(String.format(AssignCommand.MESSAGE_SUCCESS, Messages.format(validAssignment)),
                  commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAssignment), modelStub.assignmentsAdded);
    }
    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Assignment validAssignment = new AssignmentBuilder().build();
        AssignCommand assignCommand = new AssignCommand(validAssignment);
        ModelStub modelStub = new ModelStubWithAssignment(validAssignment);
        assertThrows(CommandException.class,
                AssignCommand.MESSAGE_DUPLICATE_ASSIGNMENT, () -> assignCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Assignment alphaAlice = new AssignmentBuilder().withProject(ALPHA)
                .withEmployee(ALICE).build();
        Assignment betaBenson = new AssignmentBuilder().withProject(BETA)
                .withEmployee(BENSON).build();
        AssignCommand addAlphaAliceCommand = new AssignCommand(alphaAlice);
        AssignCommand addBetaBensonCommand = new AssignCommand(betaBenson);

        // same object -> returns true
        assertTrue(addAlphaAliceCommand.equals(addAlphaAliceCommand));

        // same values -> returns true
        AssignCommand addAlphaAliceCommandCopy = new AssignCommand(alphaAlice);
        assertTrue(addAlphaAliceCommand.equals(addAlphaAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAlphaAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAlphaAliceCommand.equals(null));

        // different employee -> returns false
        assertFalse(addAlphaAliceCommand.equals(addBetaBensonCommand));
    }

    @Test
    public void toStringMethod() {
        AssignCommand assignCommand = new AssignCommand(ALICE_ALPHA);
        String expected = AssignCommand.class.getCanonicalName() + "{assignmentId="
                + ALICE_ALPHA.getAssignmentId().toString() + ", projectId="
                + ALICE_ALPHA.getProject().getId().toString()
                + ", employeeId=" + ALICE_ALPHA.getEmployee().getEmployeeId().toString() + "}";

        assertEquals(expected, assignCommand.toString());
    }

    /**
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithAssignment extends ModelStub {
        private final Assignment assignment;
        private final ArrayList<Employee> employees = new ArrayList<>();
        private final ArrayList<Project> projects = new ArrayList<>();

        ModelStubWithAssignment(Assignment assignment) {
            requireNonNull(assignment);
            this.assignment = assignment;
        }

        @Override
        public ObservableList<Employee> getEmployeeList() {
            employees.add(ALICE);
            employees.add(BENSON);
            return FXCollections.observableArrayList(employees);
        }

        @Override
        public ObservableList<Project> getProjectList() {
            projects.add(ALPHA);
            projects.add(BETA);
            return FXCollections.observableArrayList(projects);
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return this.assignment.isSameAssignment(assignment);
        }
    }

    /**
     * A Model stub that always accept the assignment being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends ModelStub {
        final ArrayList<Assignment> assignmentsAdded = new ArrayList<>();
        final ArrayList<Employee> employees = new ArrayList<>();
        final ArrayList<Project> projects = new ArrayList<>();

        @Override
        public ObservableList<Employee> getEmployeeList() {
            employees.add(ALICE);
            employees.add(BENSON);
            return FXCollections.observableArrayList(employees);
        }

        @Override
        public ObservableList<Project> getProjectList() {
            projects.add(ALPHA);
            projects.add(BETA);
            return FXCollections.observableArrayList(projects);
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return assignmentsAdded.stream().anyMatch(assignment::isSameAssignment);
        }

        @Override
        public void addAssignment(Assignment assignment) {
            requireNonNull(assignment);
            assignmentsAdded.add(assignment);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
