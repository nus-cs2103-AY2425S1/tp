package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EMAIL_DETAILS = "Invalid email format. "
          + "Please ensure your email includes a valid domain "
          + "(e.g., name@example.com)";
    public static final String MESSAGE_INVALID_PERSON_OUT_OF_BOUNDS = "The index provided is out of bounds."
            + " Please enter an index within the size of the list.";
    public static final String MESSAGE_INVALID_PERSON_DELETED = "No matching person found. Please check the details.";
    public static final String MESSSAGE_INVALID_PHONE_DETAILS = "Invalid Singapore phone number input. "
          + "Enter a valid phone number that is 8 digits long and starts with 3, 6, 8 or 9.";

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
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress());
        if (person.getTag() != null) {
            builder.append("; Tag: ").append(person.getTag());
        }

        String allergiesString = person.getAllergies()
                .stream()
                .map(Allergy::toString)
                .collect(Collectors.joining(", "));
        allergiesString = "[" + allergiesString + "]";
        builder.append("; Allergies: ").append(allergiesString);
        return builder.toString();
    }
}
