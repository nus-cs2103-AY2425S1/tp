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

    public static final String MESSAGE_DEFAULT_OVERVIEW = """
            Hmm, did you mean to type any of these instead?:
            Add: add n/NAME id/STUDENT_ID [nid/NUS_NET_ID] [m/MAJOR] [y/YEAR] [g/GROUP]
            Edit: edit INDEX [n/NAME] [id/STUDENT_ID] [nid/NUS_NET_ID][[m/MAJOR] [y/YEAR] [g/GROUP]
            Comment: comment INDEX c/COMMENT
            Find: find [n/ NAME_KEYWORDS] [id/ STUDENT_IDS]
            Show group: show GROUP_INDEX
            Delete: delete INDEX
            List: list
            Clear: clear
            Help: help""";
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command \n%1$s";
    public static final String MESSAGE_EMPTY_COMMAND_FORMAT = "There needs to be a command and/or arguments! \n%1$s";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_DELETE_EMPTY_INDEX = "Error: The index cannot be empty";
    public static final String MESSAGE_INVALID_ARUGUMENTS = "Invalid arguments! "
            + "Must be alphanumeric lowercase characters";
    public static final String MESSAGE_OVERFLOW_INDEX = "Error: Index is too large!"
                + " The largest possible value is 2147483647.";
    public static final String MESSAGE_MAXLEADINGZEROS = "Error: The index has too many digits!"
            + " The maximum amount of digits is 10 including leading zeros to be ignored";
    public static final String MESSAGE_INDEX_UPPERBOUND_ERROR = "Sorry but the index was too large "
                + "compared to your list size!";
    public static final String MESSAGE_DELETE_EMPTY_ERROR = "Sorry but you cannot delete from an empty list.";
    public static final String MESSAGE_EDIT_EMPTY_ERROR = "Sorry but you cannot edit from an empty list.";

    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_NUMBER_OF_ARGS = "There should be at least one argument";
    public static final String MESSAGE_DUPLICATE_FIELDS = "The following duplicate fields are detected and is not permitted: ";

    public static final String MESSAGE_MORE_THAN_TEN_DUPLICATE_FIELDS =
            "There should not be more than ten duplicate fields for group (g/)";
            
    public static final String MESSAGE_DUPLICATE_EXPLANATION =
            "The above is considered duplicate fields because they have a preceding space.";
    public static final String MESSAGE_DUPLICATE_SOLUTION =
            "If you are trying to use the field values as part of a string, remove the preceding space";

    public static final String MESSAGE_NAME_CANNOT_BE_EMPTY = "Name cannot be empty.";
    public static final String MESSAGE_INVALID_STUDENT_ID_FORMAT = "Invalid Student ID format. It should be 9"
            + " characters with letters at the start and end, and digits in between (e.g., 'A1234567E').";
    public static final String MESSAGE_NO_STUDENTS_FOUND = "No students found matching the criteria.";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Attendance event '%1$s' not found.";
    public static final String MESSAGE_DUPLICATE_GROUPS = "There are duplicate groups. "
            + "Group names are treated as case insensitive and with multiple spaces considered equivalent.";
    public static final String MESSAGE_INVALID_PREFIX = "If you are trying to use a prefix (eg. 'n/'), ensure there "
            + "is a space before the prefix and that it is in this list."
            + "\nSupported prefixes: %1$s";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields)
                + "\n" + MESSAGE_DUPLICATE_EXPLANATION
                + "\n" + MESSAGE_DUPLICATE_SOLUTION;
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
        boolean groupsIsEmpty = person.getGroups().isEmpty();

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

        if (!groupsIsEmpty) {
            builder.append("; Group(s): ");
            person.getGroups().forEach(builder::append);
        }

        return builder.toString();
    }



}
