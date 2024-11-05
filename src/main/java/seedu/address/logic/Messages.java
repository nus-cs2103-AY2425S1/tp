package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.contact.Contact;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String LINE_BREAK = "\n";
    public static final String WHITESPACE = " ";
    public static final String COMMAND_FORMAT_PREAMBLE = "Command Format:";
    public static final String MESSAGE_COMMAND_LIST = "add, edit, delete, find, list, help, clear, exit";
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command. "
            + "Only the following will be accepted as the first word of the command:\n"
            + MESSAGE_COMMAND_LIST;

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! %1$s";
    public static final String MESSAGE_BLANK_FIELD = "field cannot be blank!";

    public static final String MESSAGE_DUPLICATE_FIELDS_COMMAND =
            "Multiple values specified for the following single-valued field(s): ";

    public static final String MESSAGE_HELP_PROMPT = "Type `%1$s` for examples and details";

    // find and list
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";

    // edit and delete
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_INVALID_INDEX_OR_NAME = "Invalid Index or full name given. "
            + "Index must be a positive Integer only. "
            + "Full name must follow the constraints given for names. ";
    public static final String MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK =
            "This contact is not in address book. Check if Full Name is used. Check contact's full name or "
                    + "if it exists by finding. Example Command to " + FindCommand.COMMAND_WORD + ": `"
                    + FindCommand.COMMAND_WORD + " " + PREFIX_NAME + " %1$s`";
    public static final String MESSAGE_MULTIPLE_WAYS_FORBIDDEN =
            "%1$s by both index and full name is not allowed";
    public static final String MESSAGE_DUPLICATE_NAME = "There is more than 1 contact with the same "
            + "full name. Please %1$s by index.\nTip: find the contact's name to obtain their "
            + "corresponding displayed index, and %1$s by the displayed index directly on the page. "
            + "Command to find :\n`"
            + FindCommand.COMMAND_WORD + " " + PREFIX_NAME + " %2$s`";

    // edit and add
    //edits to make, contacts to add // This will result in a contact that
    public static final String MESSAGE_DUPLICATE_CONTACT = "%1$s already "
            + "exists in the address book. Check if there are any errors in %2$s or in the " +
            "details of the existing contacts";
    public static final String MESSAGE_DUPLICATE_FIELDS_CONTACT =
            "This will result in more than 1 contact having same President roles," +
                    " or having same Telegram handles/mobile number/nicknames, or having the same full " +
                    "names" +
                    " without distinct nicknames, which are not allowed."
                    + "\n%1$s";

    public static String styleCommand(String command) {
        return "`" + command + "`";
    }
    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS_COMMAND + String.join(" ", duplicateFields) + "\nwhich is not allowed.";
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
        builder.append("; Roles: ");
        contact.getRoles().forEach(builder::append);
        if (!contact.getNickname().isEmpty()) {
            builder.append("; Nickname: ");
            builder.append(contact.getNickname());
        }
        return builder.toString();
    }

}
