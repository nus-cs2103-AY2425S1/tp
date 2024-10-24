package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX = "The volunteer index provided is invalid";
    public static final String MESSAGE_VOLUNTEERS_LISTED_OVERVIEW = "%1$d volunteer listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";

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
     * Formats the {@code volunteer} for display to the user.
     */
    public static String format(Volunteer volunteer) {
        final StringBuilder builder = new StringBuilder();
        builder.append(volunteer.getName())
                .append("; Phone: ")
                .append(volunteer.getPhone())
                .append("; Email: ")
                .append(volunteer.getEmail())
                .append("; Available Date: ")
                .append(volunteer.getAvailableDate());
        return builder.toString();
    }

    /**
     * Formats an {@code Event} object into a string representation.
     *
     * @param event The event to format.
     * @return A formatted string representing the event,
     *     including its name, location, date, start time, end time, and description.
     */
    public static String format(Event event) {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getName())
                .append("; Location: ")
                .append(event.getLocation())
                .append("; Date: ")
                .append(event.getDate())
                .append("; Start Time: ")
                .append(event.getStartTime())
                .append("; End Time: ")
                .append(event.getEndTime())
                .append("; Description: ")
                .append(event.getDescription());
        return builder.toString();
    }

}
