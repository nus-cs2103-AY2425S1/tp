package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TelegramHandle;



/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command"
            + "\nEnter help to access User Guide if needed";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

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
                .append(person.getPhone().map(Phone::toString).orElse("N/A"))
                .append("; Email: ")
                .append(person.getEmail().map(Email::toString).orElse("N/A"))
                .append("; Telegram Handle: ")
                .append(person.getTelegramHandle().map(TelegramHandle::toString).orElse("N/A"))
                .append("; Module Name: ")
                .append(person.getModuleName().map(ModuleName::toString).orElse("N/A"))
                .append(": Remark: ")
                .append(person.getRemark().map(Remark::toString).orElse("N/A"))
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
