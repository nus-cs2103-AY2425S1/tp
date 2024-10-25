package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Employee> filteredEmployees;
    private final FilteredList<Project> filteredProjects;
    private final FilteredList<Assignment> filteredAssignments;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEmployees = new FilteredList<>(this.addressBook.getEmployeeList());
        filteredProjects = new FilteredList<>(this.addressBook.getProjectList());
        filteredAssignments = new FilteredList<>(this.addressBook.getAssignmentList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        setAddressBookEmployee(addressBook);
        setAddressBookProject(addressBook);
    }

    @Override
    public void setAddressBookEmployee(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetEmployeeData(addressBook);
    }

    @Override
    public void setAddressBookProject(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetProjectData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasEmployee(Employee employee) {
        requireNonNull(employee);
        return addressBook.hasEmployee(employee);
    }

    @Override
    public boolean hasEmployeeId(EmployeeId employeeId) {
        requireNonNull(employeeId);
        return addressBook.hasEmployeeId(employeeId);
    }

    @Override
    public void deleteEmployee(Employee target) {
        addressBook.removeEmployee(target);
    }

    @Override
    public void addEmployee(Employee employee) {
        addressBook.addEmployee(employee);
        updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
    }

    @Override
    public void setEmployee(Employee target, Employee editedEmployee) {
        requireAllNonNull(target, editedEmployee);

        addressBook.setEmployee(target, editedEmployee);
    }

    @Override
    public ObservableList<Employee> getEmployeeList() {
        return this.addressBook.getEmployeeList();
    }

    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return addressBook.hasProject(project);
    }

    @Override
    public boolean hasProjectId(ProjectId projectId) {
        requireNonNull(projectId);
        return addressBook.hasProjectId(projectId);
    }

    @Override
    public void deleteProject(Project target) {
        addressBook.removeProject(target);
    }

    @Override
    public void addProject(Project project) {
        addressBook.addProject(project);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
    }

    @Override
    public void setProject(Project target, Project editedProject) {
        requireAllNonNull(target, editedProject);

        addressBook.setProject(target, editedProject);
    }

    @Override
    public ObservableList<Project> getProjectList() {
        return this.addressBook.getProjectList();
    }

    @Override
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return addressBook.hasAssignment(assignment);
    }

    @Override
    public boolean hasAssignment(AssignmentId assignmentId) {
        requireNonNull(assignmentId);
        return addressBook.hasAssignment(assignmentId);
    }

    @Override
    public boolean hasAssignment(ProjectId projectId, EmployeeId employeeId) {
        requireNonNull(projectId);
        requireAllNonNull(employeeId);
        return addressBook.hasAssignment(projectId, employeeId);
    }

    @Override
    public void deleteAssignment(Assignment target) {
        addressBook.removeAssignment(target);
    }

    @Override
    public void deleteAssignment(AssignmentId targetId) {
        addressBook.removeAssignment(targetId);
    }

    @Override
    public void deleteAssignment(ProjectId targetProjectId, EmployeeId targetEmployeeId) {
        addressBook.removeAssignment(targetProjectId, targetEmployeeId);
    }

    @Override
    public void addAssignment(Assignment assignment) {
        addressBook.addAssignment(assignment);
    }
    //=========== Filtered Employee List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Employee} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Employee> getFilteredEmployeeList() {
        return filteredEmployees;
    }

    @Override
    public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
        requireNonNull(predicate);
        filteredEmployees.setPredicate(predicate);
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Project} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        return filteredProjects;
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        requireNonNull(predicate);
        filteredProjects.setPredicate(predicate);
    }

    //=========== Filtered Assignment List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Assignment} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Assignment> getFilteredAssignmentList() {
        return filteredAssignments;
    }

    @Override
    public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
        requireNonNull(predicate);
        filteredAssignments.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredEmployees.equals(otherModelManager.filteredEmployees)
                && filteredProjects.equals(otherModelManager.filteredProjects);
    }

}
