package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Employee> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Project> PREDICATE_SHOW_ALL_PROJECTS = unused -> true;
    Predicate<Assignment> PREDICATE_SHOW_ALL_ASSIGNMENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces all address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Replaces address book persons data with the data in {@code addressBook}.
     */
    void setAddressBookPerson(ReadOnlyAddressBook addressBook);

    /**
     * Replaces address book projects data with the data in {@code addressBook}.
     */
    void setAddressBookProject(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Employee person);

    /**
     * Returns true if a person with the same {@code employeeId} exists in
     * the address book.
     */
    boolean hasEmployeeId(EmployeeId employeeId);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Employee target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Employee person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Employee target, Employee editedPerson);

    /**
     * Gets list of persons in the address book.
     */
    ObservableList<Employee> getPersonList();

    /**
     * Returns true if a project with the same identity as {@code project} exists in
     * the address book.
     */
    boolean hasProject(Project project);

    /**
     * Returns true if a project with the same {@code projectId} exists in
     * the address book.
     */
    boolean hasProjectId(ProjectId projectId);

    /**
     * Deletes the given project.
     * The project must exist in the address book.
     */
    void deleteProject(Project target);

    /**
     * Adds the given project.
     * {@code project} must not already exist in the address book.
     */
    void addProject(Project project);

    /**
     * Replaces the given project {@code target} with {@code project}.
     * {@code project} must exist in the address book.
     * The person identity of {@code project} must not be the same as another existing project in the address book.
     */
    void setProject(Project target, Project editedProject);

    /**
     * Gets list of projects in the address book.
     */
    ObservableList<Project> getProjectList();

    /**
     * Returns true if an assignment with the same identity as {@code assignment}
     * exists in the address book.
     */
    boolean hasAssignment(Assignment assignment);

    /**
     * Returns true if an assignment with the same assignment id as {@code assignmentId}
     * exists in the address book.
     */
    boolean hasAssignment(AssignmentId assignmentId);

    /**
     * Returns true if an assignment with the same project id and employee id
     * as {@code projectId} and {@code employeeId}
     * exists in the address book.
     */
    boolean hasAssignment(ProjectId projectId, EmployeeId employeeId);

    /**
     * Deletes the given assignment.
     * The assignment must exist in the address book.
     */
    void deleteAssignment(Assignment target);

    /**
     * Deletes the given assignment.
     * The assignment must exist in the address book.
     */
    void deleteAssignment(AssignmentId targetId);

    /**
     * Deletes the given assignment.
     * The assignment must exist in the address book.
     */
    void deleteAssignment(ProjectId targetProjectId, EmployeeId targetEmployeeId);

    /**
     * Adds the given assignment.
     * {@code assignment} must not already exist in the address book.
     */
    void addAssignment(Assignment assignment);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Employee> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Employee> predicate);

    /** Returns an unmodifiable view of the filtered project list */
    ObservableList<Project> getFilteredProjectList();

    /**
     * Updates the filter of the filtered project list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProjectList(Predicate<Project> predicate);

    /** Returns an unmodifiable view of the filtered assignment list */
    ObservableList<Assignment> getFilteredAssignmentList();

    /**
     * Updates the filter of the filtered assignment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAssignmentList(Predicate<Assignment> predicate);
}
