package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_DELETE_EMPTY_INDEX = "Error: The index cannot be empty";
    public static final String MESSAGE_OVERFLOW_INDEX = "Error: Index is too large!"
                + " The largest possible value is 2147483647.";
    public static final String MESSAGE_DELETE_UPPERBOUND_ERROR = "Sorry but the index was too large "
                + "compared to your list size!";
    public static final String MESSAGE_DELETE_EMPTY_ERROR = "Sorry but you cannot delete from an empty list.";

    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_NAME_CANNOT_BE_EMPTY = "Name cannot be empty.";
    public static final String MESSAGE_INVALID_STUDENT_ID_FORMAT = "Invalid Student ID format. It should be 9"
            + " characters with letters at the start and end, and digits in between (e.g., 'A1234567E').";
    public static final String MESSAGE_NO_STUDENTS_FOUND = "No students found matching the criteria.";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Attendance event '%1$s' not found.";

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
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Student ID: ")
                .append(person.getStudentId())
                .append(formatOptionalFields(person));

        return builder.toString();
    }

    /**
     * Formats the optional fields for {@code person} for display to the user.
     */
    private static String formatOptionalFields(Person person) {
        final StringBuilder builder = new StringBuilder();
        boolean emailIsEmpty = person.getEmail().value.isEmpty();
        boolean majorIsEmpty = person.getMajor().value.isEmpty();
        boolean yearIsEmpty = person.getYear().value.isEmpty();

        if (!emailIsEmpty) {
            builder.append("; Email: ")
                    .append(person.getEmail());
        }

        if (!majorIsEmpty) {
            builder.append("; Major: ")
                    .append(person.getMajor());
        }

        if (!yearIsEmpty) {
            builder.append("; Year: ")
                    .append(person.getYear());
        }

        return builder.toString();
    }



}
