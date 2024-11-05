package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.contact.Contact;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unrecognised command. "
            + "Only the following will be accepted as the first word of the command:\n"
            + "add, edit, delete, find, list, help, clear, exit";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_NAME_FIELD_MISSING = "Invalid command format! MISSING 'n/' \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK =
            "This contact is not in address book, please use Full Name";
    public static final String MESSAGE_NO_PARAMETER_FOUND = "Please enter something for me to search";
    public static final String MESSAGE_EMPTY_PREFIX_FIELD = "Fields cannot be empty";

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
     * Formats the {@code contact} for display to the user.
     */
    public static String format(Contact contact) {
        final StringBuilder builder = new StringBuilder();
        builder.append(contact.getName())
                .append("; Telegram: ")
                .append(contact.getTelegramHandle())
                .append("; Email: ")
                .append(contact.getEmail())
                .append("; Student Status: ")
                .append(contact.getStudentStatus());
        if (!contact.getNickname().isEmpty()) {
            builder.append("; Nickname: ");
            builder.append(contact.getNickname());
        }
        builder.append("; Roles: ");
        contact.getRoles().forEach(builder::append);
        return builder.toString();
    }

}
