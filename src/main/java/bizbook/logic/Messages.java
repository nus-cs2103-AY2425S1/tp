package bizbook.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import bizbook.logic.parser.Prefix;
import bizbook.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid.";
    public static final String MESSAGE_INVALID_NOTE_INDEX = "The note index provided is invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_UNSUPPORTED_FILE_TYPE = "This file type is not supported. \n%1$s";
    public static final String MESSAGE_INVALID_FILE_PATH = "The following file path is invalid: %1$s";

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
     * Formats contact detail of {@code person} for display to the user.
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
                .append("; Tags: ");
        person.getTags().forEach(builder::append);

        builder.append("; Notes: ");
        person.getNotes().forEach(builder::append);

        return builder.toString();
    }

    /**
     * Formats contact detail of {@code person} to display only name and phone
     * to the user.
     */
    public static String formatShort(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(String.format(" (%s)", person.getPhone()));
        return builder.toString();
    }

}
