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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index must be a valid, "
            + "positive integer.\nAlso possible: unknown prefixes added after person index";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX_FORMAT =
            MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ".\n%1$s";
    public static final String MESSAGE_EMPTY_MONTH_PAID = "At least 1 month paid must be specified, with the prefix m/";
    public static final String MESSAGE_EMPTY_MONTH_PAID_FORMAT = MESSAGE_EMPTY_MONTH_PAID + "\n%1$s";
    public static final String MESSAGE_EMPTY_INDEX = "A person index must be provided";
    public static final String MESSAGE_EMPTY_INDEX_FORMAT = MESSAGE_EMPTY_INDEX + ".\n%1$s";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EMPTY_LIST_TO_VISUALIZE = "No persons in the displayed list to visualize.";

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
                .append("; Fees: ")
                .append(person.getFees())
                .append("; Class ID: ")
                .append(person.getClassId())
                .append("; Months Paid: ")
                .append(person.getMonthsPaidToString())
                .append("; Tags: ")
                .append(person.getTagsToString());
        return builder.toString();
    }
    /**
     * Formats the {@code person} for display to the user when marking paid
     */
    public static String markPaidFormat(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Months Paid: ")
                .append(person.getMonthsPaidToString());
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user when unmarking paid
     */
    public static String unmarkPaidFormat(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Months Paid: ")
                .append(person.getMonthsPaidToString());
        return builder.toString();
    }
}
