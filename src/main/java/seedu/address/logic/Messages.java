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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The client index provided is invalid";

    public static final String MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX =
            "The transaction index provided is invalid";
    public static final String MESSAGE_PERSONS_FOUND_OVERVIEW =
            "Listed %d client(s) whose names or companies contain any of the keywords: %s";
    public static final String MESSAGE_TRANSACTIONS_LISTED_OVERVIEW = "Listed %1$d transaction(s) of %2$s";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_AMOUNT = "Incorrect amount format or invalid amount!\n"
            + "Amount should be a number of up to 2 decimal places.\n"
            + "Amount should contain only digits (0-9), at most 1 decimal point (.) and at most 1 minus sign (-).\n"
            + "If decimal point is used, there should be at least 1 digit before and 1 digit after the decimal point.\n"
            + "If minus sign is used, it should be the first character.";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Incorrect date format or invalid date!\n"
            + "Date format: YYYY-MM-DD";
    public static final String MESSAGE_INVALID_MONTH_FORMAT = "Incorrect month format or invalid month!\n"
            + "Month format: YYYY-MM\n"
            + "Valid month is between [1, 12]. Valid year is an integer.";
    public static final String MESSAGE_INVALID_DATE_RANGE = "Invalid date range!\n"
            + "Start date must be before or equal to end date";
    public static final String MESSAGE_MUST_BE_TRANSACTION_LIST = "%1$s must only be used in transaction list view!\n"
            + "Use listt command to switch to transaction list view of selected client.";
    public static final String MESSAGE_MUST_BE_PERSON_LIST = "%1$s must only be used in client list view!\n"
            + "Use list command to switch to client list view.";

    public static final String MESSAGE_EMPTY_DESCRIPTION = "Description should not be blank!";
    public static final String MESSAGE_EMPTY_AMOUNT = "Amount should not be blank!";
    public static final String MESSAGE_EMPTY_OTHER_PARTY = "Other party should not be blank!";
    public static final String MESSAGE_EMPTY_DATE = "Date should not be blank!";

    public static final String MESSAGE_EMPTY_PERSON_LIST =
            "Current client list is empty!\n" + "%1$s command must only be used on non-empty client list.";

    public static final String MESSAGE_EMPTY_TRANSACTION_LIST =
            "Invalid command: The current transaction list is empty.";
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
                .append(String.format("%.2f", transaction.getAmount()))
                .append("; Other party: ")
                .append(transaction.getOtherParty())
                .append("; Date: ")
                .append(transaction.getDate().format(DateTimeUtil.DEFAULT_DATE_FORMATTER));

        return builder.toString();
    }



}
