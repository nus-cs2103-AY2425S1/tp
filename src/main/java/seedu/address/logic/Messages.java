package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.task.Task;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DISPLAYED_INDEX = "The index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_GROUPS_LISTED_OVERVIEW = "%1$d groups listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_STUDENT_NO_NOT_FOUND = "The student number provided is not found";
    public static final String MESSAGE_GROUP_NAME_NOT_FOUND = "The group name provided is not found";
    public static final String MESSAGE_ILLEGAL_PREFIX_USED = "Illegal prefix is used.";

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
     * Formats the {@code student} for display to the user.
     */
    public static String format(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName())
                .append("; Student Number:")
                .append(student.getStudentNumber())
                .append("; Email: ")
                .append(student.getEmail())
                .append("; Tags: ");
        student.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code group} for display to the user.
     */
    public static String format(Group group) {
        final StringBuilder builder = new StringBuilder();
        builder.append(group.getGroupName());
        return builder.toString();
    }

    /**
     * Formats the {@code studentNumber} for display to the user.
     */
    public static String format(StudentNumber studentNumber) {
        final StringBuilder builder = new StringBuilder();
        builder.append(studentNumber.value);
        return builder.toString();
    }

    /**
     * Formats the {@code groupName} for display to the user.
     */
    public static String format(GroupName groupName) {
        final StringBuilder builder = new StringBuilder();
        builder.append(groupName.fullName);
        return builder.toString();
    }

    /**
     * Formats the {@code task} for display to the user.
     */
    public static String format(Task task) {
        final StringBuilder builder = new StringBuilder();
        builder.append(task.getTaskName() + " ")
                .append("(Due: " + task.getDeadline() + ") ")
                .append(task.getStatus());
        return builder.toString();
    }

}
