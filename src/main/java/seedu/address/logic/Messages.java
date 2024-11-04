package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student index provided is invalid. "
            + "Please provide a valid index from the currently displayed student list.";
    public static final String MESSAGE_INVALID_ASSIGNMENT_INDEX = "The assignment index provided is invalid";
    public static final String MESSAGE_SCORE_EXCEEDS_MAX_SCORE = "The score exceeds the assignment's maximum score";
    public static final String MESSAGE_NEGATIVE_SCORE = "The score you have submitted is negative. "
            + "Only zero or positive scores are allowed";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "%1$d students listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_ALREADY_MARKED = "The assignment has already been marked as submitted";
    public static final String MESSAGE_ALREADY_UNMARKED = "The assignment's submission status was already "
            + "'has not submitted'";

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
                .append("; Phone: ")
                .append(student.getPhone())
                .append("; Email: ")
                .append(student.getEmail())
                .append("; Tags: ");
        student.getTags().forEach(builder::append);
        return builder.toString();
    }
    /**
     * Formats the {@code assignment} for display to the user.
     */
    public static String format(Assignment assignment) {
        final StringBuilder builder = new StringBuilder();
        builder.append(assignment.getName())
                .append("; Max Score: ")
                .append(assignment.getMaxScore());
        return builder.toString();
    }

}
