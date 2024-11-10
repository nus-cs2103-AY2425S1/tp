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
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command. " + "Only the following will be "
            + "accepted as the first word of the command:\n" + MESSAGE_COMMAND_LIST;

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! %1$s";
    public static final String MESSAGE_BLANK_FIELD = "Field cannot be blank!";

    public static final String MESSAGE_DUPLICATE_PREFIXES_COMMAND = "Multiple prefixes of the same type "
            + "specified as follows: %s  which is not allowed";

    public static final String MESSAGE_HELP_PROMPT = "Type `%1$s` for examples and details";

    public static final String MESSAGE_FIND_NAME_SUGGESTION = "Command to" + WHITESPACE + FindCommand.COMMAND_WORD + ":"
            + WHITESPACE + styleCommand(FindCommand.COMMAND_WORD + WHITESPACE + PREFIX_NAME + WHITESPACE + "%1$s");

    // list and find
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";

    // find and add
    public static final String MESSAGE_MISSING_DESCRIPTION_INPUT = "Please enter something for me to %s.";
    public static final String MESSAGE_NOTHING_AFTER_COMMAND_AND_BEFORE_PREFIX_GENERAL =
            "There must be a valid prefix immediately after" + WHITESPACE
                            + "%1$s without any non-whitespace characters";

    // find and edit
    public static final String MESSAGE_MISSING_PREFIX = "Prefix(es) for %s is missing, at least one must "
            + "be provided. Ensure correct spelling of prefix too (e.g. n/ and not /n)";

    // edit and add
    public static final String MESSAGE_DUPLICATE_CONTACT = "%1$s already " + "exists in the address book. "
            + "Check if there are any errors in %2$s or in the " + "details of the existing contacts";
    public static final String MESSAGE_DUPLICATE_FIELDS_CONTACT = "This will result in more than 1 contact "
            + "having same President roles," + " or having same Telegram handles / Email / nicknames, or "
            + "having the same full names" + " without distinct nicknames, which are not allowed." + LINE_BREAK
            + "%1$s";

    // edit and delete
    public static final String MESSAGE_MISSING_INDEX_OR_NAME = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, "Missing index or Full name. " + LINE_BREAK + "%1$s");
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is "
            + "invalid. Note: index refers to the number being found in the pages displayed";
    public static final String MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK = "This contact is not in address book. "
            + "Check if Full Name is used. Check contact's full name or " + "if it exists by finding. "
            + "Example: " + MESSAGE_FIND_NAME_SUGGESTION;
    public static final String MESSAGE_MULTIPLE_WAYS_FORBIDDEN = "%1$s by both index and full name is not "
            + "allowed";
    public static final String MESSAGE_DUPLICATE_NAME = "There is more than 1 contact with the same "
            + "full name. Please %2$s by index.\nTip: find the contact's name to obtain their "
            + "corresponding displayed index, and %2$s by the displayed index directly on the page. "
            + MESSAGE_FIND_NAME_SUGGESTION;
    public static final String MESSAGE_INVALID_NAME_FIELD_INPUT = "Invalid name field."
            + "\nRefer to user guide for valid name fields";

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

        return String.format(MESSAGE_DUPLICATE_PREFIXES_COMMAND, String.join(" ", duplicateFields));
    }

    /**
     * Formats the {@code contact} for display to the user.
     */
    public static String format(Contact contact) {
        final StringBuilder builder = new StringBuilder();
        builder.append(contact.getName()).append("; Telegram: ")
                .append(contact.getTelegramHandle()).append("; Email: ")
                .append(contact.getEmail()).append("; Student Status: ")
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
