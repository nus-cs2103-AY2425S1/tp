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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_NAME = "Person not found. "
            + "Please check the name and try again.";
    public static final String MESSAGE_MARK_SUCCESS = "%1$s is marked present for week %2$d";
    public static final String MESSAGE_INVALID_WEEK = "Invalid week number.";
    public static final String MESSAGE_MARK_ALREADY_SUCCESS = "Attendance is already marked for this student!";
    public static final String MESSAGE_UNMARK_ALREADY_SUCCESS = "Attendance is already unmarked for this student";
    public static final String MESSAGE_UNMARK_SUCCESS = "%1$s is marked as absent for week %2$d";
    public static final String MESSAGE_INVALID_ASSIGNMENT_NAME = "Invalid assignment name: %1$s";


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
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Telegram: ")
                .append(person.getTelegram())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        builder.append("; Github: ")
                .append(person.getGithub());
        return builder.toString();
    }

}
