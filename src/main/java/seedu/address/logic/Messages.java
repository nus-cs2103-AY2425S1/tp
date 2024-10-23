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

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unrecognised command. "
            + "Only the following will be accepted as the first word of the command:\n"
            + "add, edit, delete, find, list, help, clear, exit";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_NAME_FIELD_MISSING = "Invalid command format! MISSING 'n/' \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_NO_PARAMETER_FOUND = "Please enter something for me to search";
    public static final String MESSAGE_EMPTY_PREFIX_FIELD = "Fields cannot be empty";
    public static final String MESSAGE_PERSON_NOT_IN_ADDRESS_BOOK =
            "This person is not in address book, please use Full Name";
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
                .append("; Telegram: ")
                .append(person.getTelegramHandle())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Student Status: ")
                .append(person.getStudentStatus());
        if (!person.getNickname().isEmpty()) {
            builder.append("; Nickname: ");
            builder.append(person.getNickname());
        }
        builder.append("; Roles: ");
        person.getRoles().forEach(builder::append);
        return builder.toString();
    }

}
