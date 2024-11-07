package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command! \n"
            + "Please use the help command to see all available commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Client not found. \n"
            + "Please double check the name of the client!";
    public static final String MESSAGE_ADDRESS_NOT_FOUND = "Client not found. \n"
            + "Please double check the address of the client!";
    public static final String MESSAGE_PHONE_NOT_FOUND = "Client not found. \n"
            + "Please double check the phone number of the client!";
    public static final String MESSAGE_CLIENT_TYPE_NOT_FOUND = "Client not found. \n"
            + "Please double check the client type(s) of the client!";
    public static final String MESSAGE_VAGUE_DELETE = "Please be more specific in the name \n"
                                + "or use $ to indicate the end of an EXACT name";
    public static final String MESSAGE_PERSON_LISTED_OVERVIEW_FOR_VIEW = "%1$d client found for viewing!";
    public static final String MESSAGE_NO_PERSON_FOUND_FOR_VIEW =
            "No clients found please use the list command to see all clients";
    public static final String MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX = "The reminder index provided is invalid";
    public static final String MESSAGE_TARGET_DELETE_HAS_REMINDER = "The client has reminders, "
            + "please delete them first";
    public static final String MESSAGE_NO_CLIENT_TYPE =
            "There should be at least one CLIENT_TYPE at all times";
    public static final String MESSAGE_EDIT_PERSON_HAS_REMINDER = "The client has reminders, please delete them first!";

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
     * Returns an overview message of the number of persons listed.
     * The message will use the singular form "person" if the count is 1,
     * and the plural form "persons" otherwise.
     *
     * @param count The number of persons listed.
     * @return A formatted string with the count and the correct singular or plural term.
     */
    public static String getMessagePersonsListedOverview(int count) {
        if (count == 0) {
            return "0 client listed!, please use the list command to see all clients!";
        }
        if (count == 1) {
            return "1 client listed!";
        }
        return String.format("%1$d clients listed!", count);
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
                .append("; Client Types: ");
        person.getClientTypes().forEach(builder::append);
        builder.append("; Description: ")
                .append(person.getDescription());
        return builder.toString();
    }

    /**
     * Formats a {@code Reminder} object into a specific string representation.
     *
     * <p>The formatted string includes the person associated with the reminder,
     * the date and time of the reminder, and a description of the reminder, separated by semicolons.
     *
     * @param reminder The {@code Reminder} object to format.
     * @return A formatted string containing the person's name, date and time, and description of the reminder.
     */
    public static String format(Reminder reminder) {
        final StringBuilder builder = new StringBuilder();
        builder.append(reminder.getPersonName())
                .append("; Date and Time: ")
                .append(reminder.getFormattedDateTime())
                .append("; Description: ")
                .append(reminder.getDescription());
        return builder.toString();
    }
}
