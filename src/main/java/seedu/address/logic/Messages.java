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
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d people listed!";
    public static final String MESSAGE_PERSON_LISTED_OVERVIEW = "1 person listed!";
    public static final String MESSAGE_MEMBERS_LISTED_OVERVIEW = "%1$d Member(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_TELEGRAM = "Cannot find member(s) with telegram handle: %1$s";
    public static final String MESSAGE_NONMEMBER_ATTENDANCE =
            "Cannot mark attendance for contact that is not a member. "
                    + "Remove non-member contact from the list and try again.";
    public static final String MESSAGE_NO_ADDITIONAL_PARAMS = "Warning: %1$s command takes in no additional parameters";
    public static final String MESSAGE_INVALID_FAVOURITE_LABEL = "Invalid format: "
            + "'f/' should not be followed by any other characters.";
    public static final String MESSAGE_INVALID_NOT_FAVOURITE_LABEL = "Invalid format: "
            + "'nf/' should not be followed by any other characters.";

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
                .append("; Telegram: ")
                .append(person.getTelegram())
                .append("; Roles: ");
        person.getRoles().forEach(builder::append);
        return builder.toString();
    }

}
