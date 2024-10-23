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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_TUTORIAL_DISPLAYED_SUBJECT = "The tutorial code provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EMPTY_INPUT =
            "Some inputs are missing! Please ensure all prefixes are followed by valid inputs. \n%1$s";
    public static final String MESSAGE_INVALID_WITH_SPACES = "At least one of the inputs should not contain spaces. "
            + "Please check the correct usage. \n%1$s";
    public static final String MESSAGE_INVALID_NUMBER_OF_INPUTS = "Please input exactly two words after attend/ prefix."
            + " Please check the correct usage. \n%1$s";
    public static final String MESSAGE_INVALID_DATE_RANGE = "Start date must be before or equal to end date."
            + " Please check the correct usage. \n%1$s";
    public static final String MESSAGE_INVALID_DATE = "At least one of the dates is not in the right format."
            + " Please check the correct usage. \n%1$s";

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
                .append("; Payment: ")
                .append(person.getPayment())
                .append("; Attendance: ")
                .append(person.getParticipation())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
