package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.Transaction;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";

    public static final String MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX =
            "The transaction index provided is invalid";
    public static final String MESSAGE_PERSONS_FOUND_OVERVIEW =
            "Listed %d person(s) whose names or companies contain any of the keywords: %s";
    public static final String MESSAGE_TRANSACTIONS_LISTED_OVERVIEW = "Listed %1$d transaction(s) of %2$s";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format!\nDate format: yyyy-MM-DD";
    public static final String MESSAGE_INVALID_MONTH_FORMAT = "Incorrect month format or invalid month!\n"
            + "Month format: yyyy-mm\nValid month is between [1, 12]. Valid year is a positive integer.";
    public static final String MESSAGE_INVALID_DATE_RANGE = "Invalid date range!\n"
            + "Start date must be before or equal to end date";
    public static final String MESSAGE_MUST_BE_TRANSACTION_LIST = "%1$s must only be used in transaction list view!";
    public static final String MESSAGE_MUST_BE_PERSON_LIST = "%1$s must only be used in person list view!";

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
                .append("; Company: ")
                .append(person.getCompany())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code transaction} for display to the user.
     */
    public static String format(Transaction transaction) {
        final StringBuilder builder = new StringBuilder();
        builder.append(transaction.getDescription())
                .append("; Amount: ")
                .append(transaction.getAmount())
                .append("; Other party: ")
                .append(transaction.getOtherParty())
                .append("; Date: ")
                .append(transaction.getDate().format(DateTimeUtil.DEFAULT_DATE_FORMATTER));

        return builder.toString();
    }



}
