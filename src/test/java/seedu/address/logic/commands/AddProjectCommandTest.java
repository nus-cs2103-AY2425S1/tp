package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProjects.ALPHA;

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
import seedu.address.testutil.ProjectBuilder;

public class AddProjectCommandTest {

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProjectCommand(null));
    }

    @Test
    public void execute_projectAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProjectAdded modelStub = new ModelStubAcceptingProjectAdded();
        Project validProject = new ProjectBuilder().build();

        CommandResult commandResult = new AddProjectCommand(validProject).execute(modelStub);

        assertEquals(String.format(AddProjectCommand.MESSAGE_SUCCESS, Messages.format(validProject)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProject), modelStub.projectsAdded);
    }

    @Test
    public void execute_duplicateProject_throwsCommandException() {
        Project validProject = new ProjectBuilder().build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(validProject);
        ModelStub modelStub = new ModelStubWithProject(validProject);

        assertThrows(CommandException.class, AddProjectCommand.MESSAGE_DUPLICATE_PROJECT, (
                ) -> addProjectCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Project alpha = new ProjectBuilder().withName("Project Alpha").withId("A0276123J").build();
        Project beta = new ProjectBuilder().withName("Project Alpha").withId("A0276123K").build();
        AddProjectCommand addAlphaCommand = new AddProjectCommand(alpha);
        AddProjectCommand addBetaCommand = new AddProjectCommand(beta);

        // same object -> returns true
        assertTrue(addAlphaCommand.equals(addAlphaCommand));

        // same values -> returns true
        AddProjectCommand addAlphaCommandCopy = new AddProjectCommand(alpha);
        assertTrue(addAlphaCommand.equals(addAlphaCommandCopy));

        // different types -> returns false
        assertFalse(addAlphaCommand.equals(1));

        // null -> returns false
        assertFalse(addAlphaCommand.equals(null));

        // different employee -> returns false
        assertFalse(addAlphaCommand.equals(addBetaCommand));
    }

    @Test
    public void toStringMethod() {
        AddProjectCommand addProjectCommand = new AddProjectCommand(ALPHA);
        String expected = AddProjectCommand.class.getCanonicalName() + "{toAdd=" + ALPHA + "}";
        assertEquals(expected, addProjectCommand.toString());
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
    private class ModelStubWithProject extends ModelStub {
        private final Project project;

        ModelStubWithProject(Project project) {
            requireNonNull(project);
            this.project = project;
        }

        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return this.project.isSameProject(project);
        }
    }

    /**
     * A Model stub that always accept the employee being added.
     */
    private class ModelStubAcceptingProjectAdded extends ModelStub {
        final ArrayList<Project> projectsAdded = new ArrayList<>();

        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return projectsAdded.stream().anyMatch(project::isSameProject);
        }

        @Override
        public void addProject(Project project) {
            requireNonNull(project);
            projectsAdded.add(project);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
