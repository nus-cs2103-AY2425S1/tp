package careconnect.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import careconnect.logic.parser.Prefix;
import careconnect.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_TOO_SHORT_SEARCH = "Searched name must be at least 2 "
            + "characters long. \n%1$s";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index "
            + "provided is invalid";
    public static final String MESSAGE_INVALID_LOG_INDEX = "The log index "
            + "provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_NO_AUTOCOMPLETE_OPTIONS = "No available commands match for"
            + " autocomplete! \n%1$s";

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
                .append("; AppointmentDate: ")
                .append(person.getAppointmentDate())
                .append("; Tags: ");

        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
