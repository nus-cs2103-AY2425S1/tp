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

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The Client index provided is invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d Clients listed!";
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
                .append(person.getAddress())
                .append("; Issues: ");
        person.getIssues().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code person}'s car for display to the user.
     */
    public static String formatCar(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getCar().getVrn())
                .append("; VIN: ")
                .append(person.getCar().getVin())
                .append("; Car Make: ")
                .append(person.getCar().getCarMake())
                .append("; Car Model: ")
                .append(person.getCar().getCarModel());
        return builder.toString();
    }

    /**
     * Formats the command success message for display to the user.
     * If the user has a Car, include the VRN in the message.
     */
    public static String formatSuccessMessage(Person person, String successMessage) {
        final StringBuilder builder = new StringBuilder();
        builder.append(successMessage);
        builder.append(": ");
        builder.append(person.getName());
        if (person.getCar() == null) {
            builder.append(".");
        } else {
            builder.append(" (VRN: ");
            builder.append(person.getVrn());
            builder.append(").");
        }
        return builder.toString();
    }
}
