package seedu.address.logic;

import static seedu.address.logic.commands.DeleteCommand.MESSAGE_USAGE;

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

    public static final String MESSAGE_INVALID_INDEX_OVER_SIZE =
            "The index provided is greater than the max students \n"
            + MESSAGE_USAGE;
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    public static final String MESSAGE_GROUPS_LISTED_OVERVIEW = "%1$d groups listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns a message indicating the number of people listed.
     */
    public static String getMessagePersonsListedOverview(int numOfPeople) {
        if (numOfPeople <= 1) {
            return numOfPeople + " person listed!";
        } else {
            return numOfPeople + " people listed!";
        }
    }

    /**
     * Returns a message indicating the number of groups listed.
     */
    public static String getMessageGroupsListedOverview(int numOfGroup) {
        if (numOfGroup <= 2) {
            return numOfGroup + " group listed!";
        } else {
            return numOfGroup + " groups listed!";
        }
    }

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
                .append("; Class: ")
                .append(person.getStudentClass())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Tags: ");
        person.getTagSet().forEach(builder::append);
        return builder.toString();
    }

}
