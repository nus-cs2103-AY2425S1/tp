package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_PREFIXES = "Invalid prefixes detected: ";

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
     * Returns an error message indicating the invalid prefixes.
     * @param invalidPrefixes The list of invalid prefixes.
     * @return The error message.
     */
    public static String getErrorMessageForInvalidPrefixes(List<Prefix> invalidPrefixes) {
        assert !invalidPrefixes.isEmpty();

        return invalidPrefixes.stream()
                .map(Prefix::toString)
                .collect(Collectors.joining(" ", MESSAGE_INVALID_PREFIXES, ""));
    }

    /**
     * Formats the {@code student} for display to the user.
     */
    public static String format(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName())
                .append("; Phone: ")
                .append(student.getPhone())
                .append("; Tutorial Group: ")
                .append(student.getTutorialGroup())
                .append("; Student Number: ")
                .append(student.getStudentNumber());

        return builder.toString();
    }

    /**
     * Formats the {@code studentName} for display to the user.
     */
    public static String formatStudentName(Student student) {
        return student.getName().toString();
    }
}
