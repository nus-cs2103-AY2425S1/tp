package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX = "The employee index provided is invalid";
    public static final String MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX = "The project index provided is invalid";
    public static final String MESSAGE_EMPLOYEES_LISTED_OVERVIEW = "%1$d employees listed!";
    public static final String MESSAGE_PROJECTS_LISTED_OVERVIEW = "%1$d projects listed!";
    public static final String MESSAGE_PROJECT_MEMBERS_LISTED = "%1$d project members listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code employee} for display to the user.
     */
    public static String format(Employee employee) {
        final StringBuilder builder = new StringBuilder();
        builder.append(employee.getName())
                .append("; Employee ID: ")
                .append(employee.getEmployeeId())
                .append("; Phone: ")
                .append(employee.getPhone())
                .append("; Email: ")
                .append(employee.getEmail())
                .append("; Address: ")
                .append(employee.getAddress())
                .append("; Tags: ");
        employee.getTags().forEach(builder::append);
        builder.append("; Skills: ");
        employee.getSkills().forEach(builder::append);

        return builder.toString();
    }

    /**
     * Formats the {@code project} for display to the user.
     */
    public static Object format(Project project) {
        final StringBuilder builder = new StringBuilder();
        builder.append(project.getName())
                .append("; Project ID: ")
                .append(project.getId());

        return builder.toString();
    }

    /**
     * Formats the {@code assignment} for display to the user.
     */
    public static String format(Assignment assignment) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Assignment Id:\n")
                .append(assignment.getAssignmentId())
                .append("\nProject Id:\n")
                .append(assignment.getProject().getId())
                .append("\nEmployee Id:\n")
                .append(assignment.getEmployee().getEmployeeId());
        return builder.toString();
    }
}
