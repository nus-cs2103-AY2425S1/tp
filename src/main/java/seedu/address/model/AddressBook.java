package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.assignment.UniqueAssignmentList;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.UniqueEmployeeList;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.model.project.UniqueProjectList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEmployeeList persons;
    private final UniqueProjectList projects;
    private final UniqueAssignmentList assignments;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueEmployeeList();
        projects = new UniqueProjectList();
        assignments = new UniqueAssignmentList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        setPersons(toBeCopied.getPersonList());
        setProjects(toBeCopied.getProjectList());
        setAssignments(toBeCopied.getAssignmentList());
        resetPersonData(toBeCopied);
        resetProjectData(toBeCopied);
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Employee> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the project list with {@code projects}.
     * {@code projects} must not contain duplicate projects.
     */
    public void setProjects(List<Project> projects) {
        this.projects.setProjects(projects);
    }

    /**
     * Replaces the contents of the assignment list with {@code assignments}.
     * {@code assignments} must not contain duplicate assignments.
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments.setAssignments(assignments);
    }

    /**
     * Resets all data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        resetPersonData(newData);
        resetProjectData(newData);
    }

    /**
     * Resets the existing persons data of this {@code AddressBook} with {@code newData}.
     */
    public void resetPersonData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    /**
     * Resets the existing projects data of this {@code AddressBook} with {@code newData}.
     */
    public void resetProjectData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setProjects(newData.getProjectList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Employee person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a person with the same {@code employeeId} exists in
     * the address book.
     */
    public boolean hasEmployeeId(EmployeeId employeeId) {
        requireNonNull(employeeId);
        return persons.containsId(employeeId);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Employee p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Employee target, Employee editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Employee key) {
        persons.remove(key);
    }

    //// project-level operations

    /**
     * Returns true if a project with the same identity as {@code project} exists in the address book.
     */
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projects.contains(project);
    }

    /**
     * Returns true if a project with the same {@code projectId} exists in
     * the address book.
     */
    public boolean hasProjectId(ProjectId projectId) {
        requireNonNull(projectId);
        return projects.containsId(projectId);
    }

    /**
     * Adds a project to the address book.
     * The project must not already exist in the address book.
     */
    public void addProject(Project p) {
        projects.add(p);
    }

    /**
     * Replaces the given project {@code target} in the list with {@code editedProject}.
     * {@code target} must exist in the address book.
     * The project identity of {@code editedProject} must not be the same as another existing project in the
     * address book.
     */
    public void setProject(Project target, Project editedProject) {
        requireNonNull(editedProject);
        projects.setProject(target, editedProject);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProject(Project key) {
        projects.remove(key);
    }

    /**
     * Returns true if a project with the same identity as {@code project} exists in the address book.
     */
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return assignments.contains(assignment);
    }

    /**
     * Returns true if an assignment with the same assignment id as {@code assignmentId}
     * exists in the address book.
     */
    public boolean hasAssignment(AssignmentId assignmentId) {
        requireNonNull(assignmentId);
        return assignments.contains(assignmentId);
    }

    /**
     * Returns true if an assignment with the same project id and employee id
     * as {@code projectId} and {@code employeeId}
     * exists in the address book.
     */
    public boolean hasAssignment(ProjectId projectId, EmployeeId employeeId) {
        requireNonNull(projectId);
        requireAllNonNull(employeeId);
        return assignments.contains(projectId, employeeId);
    }

    /**
     * Adds an assignment to the address book.
     * The assignment must not already exist in the address book.
     */
    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    /**
     * Removes {@code assignment} from this {@code AddressBook}.
     * {@code assignment} must exist in the address book.
     */
    public void removeAssignment(Assignment assignment) {
        requireNonNull(assignment);
        assignments.remove(assignment);
    }

    /**
     * Removes {@code assignment} from this {@code AddressBook}.
     * {@code assignment} must exist in the address book.
     */
    public void removeAssignment(AssignmentId assignmentId) {
        requireNonNull(assignmentId);
        assignments.remove(assignmentId);
    }

    /**
     * Removes {@code assignment} from this {@code AddressBook}.
     * {@code assignment} must exist in the address book.
     */
    public void removeAssignment(ProjectId projectId, EmployeeId employeeId) {
        requireNonNull(projectId);
        requireAllNonNull(employeeId);
        assignments.remove(projectId, employeeId);
    }
    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("projects", projects)
                .toString();
    }

    @Override
    public ObservableList<Employee> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Project> getProjectList() {
        return projects.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Assignment> getAssignmentList() {
        return assignments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
