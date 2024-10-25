package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_EMPLOYEE = "Employees list contains duplicate employee(s).";
    public static final String MESSAGE_DUPLICATE_PROJECT = "Projects list contains duplicate project(s).";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Projects list contains duplicate assignment(s).";

    private final List<JsonAdaptedEmployee> employees = new ArrayList<>();
    private final List<JsonAdaptedProject> projects = new ArrayList<>();
    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given employees and projects.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("employees") List<JsonAdaptedEmployee> employees,
            @JsonProperty("projects") List<JsonAdaptedProject> projects,
            @JsonProperty("assignments") List<JsonAdaptedAssignment> assignments
    ) {
        this.employees.addAll(employees);

        if (projects != null) {
            this.projects.addAll(projects);
        }

        if (assignments != null) {
            this.assignments.addAll(assignments);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        employees.addAll(source.getEmployeeList().stream().map(JsonAdaptedEmployee::new).collect(Collectors.toList()));
        projects.addAll(source.getProjectList().stream().map(JsonAdaptedProject::new).collect(Collectors.toList()));
        assignments.addAll(source.getAssignmentList()
                .stream().map(JsonAdaptedAssignment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedEmployee jsonAdaptedEmployee : employees) {
            Employee employee = jsonAdaptedEmployee.toModelType();
            if (addressBook.hasEmployee(employee)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EMPLOYEE);
            }
            addressBook.addEmployee(employee);
        }
        for (JsonAdaptedProject jsonAdaptedProject : projects) {
            Project project = jsonAdaptedProject.toModelType();
            if (addressBook.hasProject(project)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROJECT);
            }
            addressBook.addProject(project);
        }
        for (JsonAdaptedAssignment jsonAdaptedAssignment : assignments) {
            Assignment assignment = jsonAdaptedAssignment.toModelType(addressBook);
            if (addressBook.hasAssignment(assignment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ASSIGNMENT);
            }
            addressBook.addAssignment(assignment);
        }
        return addressBook;
    }

}
