package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.role.Role;
import seedu.address.model.wedding.Wedding;

/**
 * Container for user visible messages.
 */
public class Messages {

    // General Incorrect Command Messages
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    // Parsing Messages
    // Parse by index usages
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
            "The person index %1$d provided is invalid, please enter an index that is between 1 and %2$d";
    public static final String MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX =
            "The wedding index %d provided is invalid, please "
            + "enter an index that is between 1 and %1$d";

    // Parse by name usages
    public static final String MESSAGE_INVALID_PERSON = "This person does not exist in the address book";


    // List Command Messages
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_PERSONS_LISTED_NAME_OVERVIEW = "%1$d persons listed with name %2$s!";


    // Duplicate Messages
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";
    public static final String MESSAGE_PHONE_EXIST = "This number already exists in the address book";
    public static final String MESSAGE_EMAIL_EXIST = "This email already exists in the address book";


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
                .append(" | Phone: ")
                .append(person.getPhone())
                .append(" | Email: ")
                .append(person.getEmail())
                .append(" | Address: ")
                .append(person.getAddress())
                .append(" | Role: ")
                .append(person.getRole().map(Role::toString).orElse("NA"));
        return builder.toString();
    }

    /**
     * Formats the {@code wedding} for display to the user.
     */
    public static String format(Wedding wedding) {
        final StringBuilder builder = new StringBuilder();
        builder.append(wedding.getName())
                .append(" | Client: ")
                .append(wedding.getClient().getPerson().getName().fullName)
                .append(" | Date: ")
                .append(wedding.getDate() == null ? "NA" : wedding.getDate())
                .append(" | Venue: ")
                .append(wedding.getVenue() == null ? "NA" : wedding.getVenue());
        return builder.toString();
    }

    /**
     * Formats the list of {@code Wedding} for display to the user.
     */
    public static String format(Set<Wedding> weddingJobs) {
        return weddingJobs.stream()
                .map(Messages::format) // Calls the static format method in Wedding for each wedding
                .collect(Collectors.joining(", \n"));
    }
}
