package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.CommandTextHistory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
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
    public Path getCommandTextHistoryFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCommandTextHistoryFilePath(Path commandTextHistoryFilePath) {
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
    public void setAddressBookAssignments(ReadOnlyAddressBook addressBook) {
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
        return true;
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
    public boolean hasProject(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasProjectId(ProjectId projectId) {
        return true;
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
    public ObservableList<Employee> getEmployeeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Project> getProjectList() {
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

    @Override
    public boolean deleteAllAssignments(EmployeeId targetEmployeeId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean deleteAllAssignments(ProjectId targetProjectId) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CommandTextHistory getCommandTextHistory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCommandTextHistory(CommandTextHistory commandTextHistory) {
        throw new AssertionError("This method should not be called.");
    }
}
