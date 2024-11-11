package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format!";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_DUPLICATE_INDEXES =
                "Duplicate indexes found";
    public static final String MESSAGE_ATTENDEE_NOT_FOUND =
                "Attendee was not found in the address book";
    public static final String MESSAGE_INVALID_DATES =
                "Event start date must be before event end date!";



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
                .append("; Relationship: ")
                .append(person.getRelationship());
        return builder.toString();
    }

    /**
     * Formats the {@code event} for display to the user.
     */
    public static String formatEvent(Event event) {
        final StringBuilder builder = new StringBuilder();
        StringBuilder attendeesString = new StringBuilder();

        for (Person attendee : event.getAttendees()) {
            attendeesString.append('\n').append(attendee.toDisplayString());
        }

        builder.append(event.getEventName())
                .append("; Date: ")
                .append(event.getStartDate())
                .append(" - ")
                .append(event.getEndDate())
                .append("; Location: ")
                .append(event.getLocation())
                .append(event.getAttendees().isEmpty() ? "; \nNo Attendees." : "; \nAttendees: " + attendeesString);

        return builder.toString();
    }

}
