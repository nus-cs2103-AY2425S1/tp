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
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid.";
    public static final String MESSAGE_MULTIPLE_INVALID_CONTACT_DISPLAYED_INDEX = "One or more of the contact "
            + "indices provided is invalid.";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is greater than the "
            + "total number of events in your event list now.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d contacts listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EMPTY_CONTACTS_ON_ADDALL = "There needs to be at least one contact in the "
            + "search panel to add to the event.";
    public static final String MESSAGE_USER_SEARCH_QUERY_ROLES = "Displaying search results for all contacts "
            + "with the roles: %s";

    public static final String SEARCHMODE_UNKNOWN_COMMAND = "\nYou are in search-mode."
            + "\nUse only search, exit-search(es), "
            + "add-all(aa), exclude, check-excluded(chx), clear-excluded(clx) or exit.";

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
                .append(person.getAddress());

        appendTelegramUsernameToMsg(person, builder);

        appendRolesToMsg(person, builder);

        return builder.toString();
    }

    private static void appendTelegramUsernameToMsg(Person person, StringBuilder builder) {
        if (person.getTelegramUsername().toString() != null) {
            builder.append("; Telegram username: ");
            builder.append(person.getTelegramUsername());
        }
    }

    private static void appendRolesToMsg(Person person, StringBuilder builder) {
        if (!person.getRoles().isEmpty()) {
            builder.append("; Roles: ");
            person.getRoles().forEach(builder::append);
        }
    }

}
