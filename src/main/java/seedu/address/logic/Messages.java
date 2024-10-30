package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

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
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person cannot be found in the clientHub";
    public static final String MESSAGE_VAGUE_DELETE = "Please be more specific in the name";
    public static final String MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW = "%1$d person found for viewing!";
    public static final String MESSAGE_NO_PERSON_FOUND_FOR_VIEW =
            "No clients found please use the list command to see all clients";

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
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Client Types: ");
        person.getClientTypes().forEach(builder::append);
        builder.append("; Description: ")
                .append(person.getDescription());
        return builder.toString();
    }

    /**
     * Formats a {@code Reminder} object into a specific string representation.
     *
     * <p>The formatted string includes the person associated with the reminder,
     * the date and time of the reminder, and a description of the reminder, separated by semicolons.
     *
     * @param reminder The {@code Reminder} object to format.
     * @return A formatted string containing the person's name, date and time, and description of the reminder.
     */
    public static String format(Reminder reminder) {
        final StringBuilder builder = new StringBuilder();
        builder.append(reminder.getPerson())
                .append("; Date and Time: ")
                .append(reminder.getDateTime())
                .append("; Description: ")
                .append(reminder.getDescription());
        return builder.toString();
    }
}
