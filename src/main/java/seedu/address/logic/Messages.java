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
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    public static final String MESSAGE_MARK_SUCCESS = "Marked present in Tutorial(s): %2$s for Person: %1$s";
    public static final String MESSAGE_MARK_UNNECESSARY =
            "Person: %1$s is already marked as present for Tutorial: %2$s";
    public static final String MESSAGE_UNMARK_UNNECESSARY = "Person: %1$s is already marked absent from Tutorial %2$s";
    public static final String MESSAGE_UNMARK_SUCCESS = "Marked absent from Tutorial %2$s for Person: %1$s";

    public static final String MESSAGE_RESET_UNNECESSARY =
            "Tutorial: %2$s is already marked as not taken place for Person: %1$s";

    public static final String MESSAGE_RESET_SUCCESS = "Marked not taken place in Tutorial: %2$s for Person: %1$s";

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
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString().trim();
    }

}
