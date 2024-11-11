package seedu.eventtory.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.eventtory.logic.parser.Prefix;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX = "The vendor index provided is invalid";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_VENDOR_NOT_ASSIGNED_TO_EVENT =
            "The specified event does not contain this vendor";
    public static final String MESSAGE_EVENT_DOES_NOT_CONTAIN_VENDOR =
            "The specified vendor is not assigned to this Event";
    public static final String MESSAGE_VENDOR_ALREADY_ASSIGNED_TO_EVENT =
            "The specified event already contains this vendor";
    public static final String MESSAGE_EVENT_ALREADY_CONTAINS_VENDOR =
            "The specified vendor is already assigned to this Event";
    public static final String MESSAGE_NO_VENDORS_FOUND = "No vendors found!";
    public static final String MESSAGE_NO_EVENTS_FOUND = "No events found!";
    public static final String MESSAGE_VENDORS_LISTED_OVERVIEW = "%1$d vendors listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
        "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code vendor} for display to the user.
     */
    public static String format(Vendor vendor) {
        final StringBuilder builder = new StringBuilder();
        builder.append(vendor.getName())
                .append("; Phone: ")
                .append(vendor.getPhone())
                .append("; Description: ")
                .append(vendor.getDescription())
                .append("; Tags: ");
        vendor.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code event} for display to the user.
     */
    public static String format(Event event) {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getName())
                .append("; Date: ")
                .append(event.getDate())
                .append("; Tags: ");
        event.getTags().forEach(builder::append);
        return builder.toString();
    }
}
