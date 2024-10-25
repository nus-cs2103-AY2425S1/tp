package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEmployees.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.testutil.EmployeeBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_employeeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEmployeeAdded modelStub = new ModelStubAcceptingEmployeeAdded();
        Employee validEmployee = new EmployeeBuilder().build();

        CommandResult commandResult = new AddCommand(validEmployee).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validEmployee)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validEmployee), modelStub.employeesAdded);
    }

    @Test
    public void execute_duplicateEmployee_throwsCommandException() {
        Employee validEmployee = new EmployeeBuilder().build();
        AddCommand addCommand = new AddCommand(validEmployee);
        ModelStub modelStub = new ModelStubWithEmployee(validEmployee);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_EMPLOYEE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Employee alice = new EmployeeBuilder().withName("Alice").build();
        Employee bob = new EmployeeBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different employee -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEmployee(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookEmployee(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookProject(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmployee(Employee employee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEmployeeId(EmployeeId employeeId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEmployee(Employee target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEmployee(Employee target, Employee editedEmployee) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Employee> getEmployeeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProjectId(ProjectId projectId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProject(Project target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProject(Project target, Project editedproject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getProjectList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(AssignmentId assignmentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(ProjectId projectId, EmployeeId employeeId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(ProjectId projectId, EmployeeId employeeId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(AssignmentId target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(Assignment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assignment> getFilteredAssignmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProjectList(Predicate<Project> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single employee.
     */
    private class ModelStubWithEmployee extends ModelStub {
        private final Employee employee;

        ModelStubWithEmployee(Employee employee) {
            requireNonNull(employee);
            this.employee = employee;
        }

        @Override
        public boolean hasEmployee(Employee employee) {
            requireNonNull(employee);
            return this.employee.isSameEmployee(employee);
        }
    }

    /**
     * A Model stub that always accept the employee being added.
     */
    private class ModelStubAcceptingEmployeeAdded extends ModelStub {
        final ArrayList<Employee> employeesAdded = new ArrayList<>();

        @Override
        public boolean hasEmployee(Employee employee) {
            requireNonNull(employee);
            return employeesAdded.stream().anyMatch(employee::isSameEmployee);
        }

        @Override
        public void addEmployee(Employee employee) {
            requireNonNull(employee);
            employeesAdded.add(employee);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
