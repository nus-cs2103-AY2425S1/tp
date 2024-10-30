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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_NAME = "The person name provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_POLICY_FORMAT = "The policy name format is invalid.";
    public static final String MESSAGE_INVALID_POLICY_DISPLAYED_INDEX = "The policy index provided is invalid";
    public static final String MESSAGE_DUPLICATE_POLICY_NAME = "The policy name provided is duplicated";
    public static final String MESSAGE_DUPLICATE_POLICY_INDEX = "Multiple identical policy index specified to edit";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "The date format is invalid. "
            + "Please use yyyy-MM-dd format.";
    public static final String MESSAGE_INVALID_DATERANGE_FORMAT = "The date range format is invalid. "
            + "Please use yyyy-MM-dd to yyyy-MM-dd format.";
    public static final String MESSAGE_START_DATE_AFTER_END_DATE = "Start date cannot be after end date.";
    public static final String MESSAGE_SUCCESS_SEARCH_BIRTHDAY = "Listed all clients with birthdays %s";
    public static final String MESSAGE_SUCCESS_SEARCH_APPOINTMENT = "Listed all clients with appointments %s";
    public static final String MESSAGE_INVALID_DATETIME_FORMAT = "The date format is invalid. "
            + "Please use yyyy-MM-dd HH:mm format.";
    public static final String MESSAGE_INVALID_DATETIMERANGE_FORMAT = "The date-time range format is invalid. "
            + "Please use 'yyyy-MM-dd HH:mm to yyyy-MM-dd HH:mm' format.";
    public static final String MESSAGE_START_DATETIME_AFTER_END_DATETIME = "Start date-time cannot be "
            + "after end date-time.";
    public static final String MESSAGE_SUCCESS_SEARCH_POLICY = "Listed all clients with policy: %s";

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
                .append("; Birthday: ")
                .append(person.getBirthday())
                .append("; Appointment: ")
                .append(person.getAppointment())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
