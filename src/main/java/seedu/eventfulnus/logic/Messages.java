package seedu.eventfulnus.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.eventfulnus.logic.parser.Prefix;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command: ";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid!";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_HELP = """
            EventfulNUS allows you to manage a list of people and events, for easier event planning for\s
            NUS Inter-Faculty Games.

            Find out more about using EventfulNUS on our User Guide at:
            https://ay2425s1-cs2103t-w14-4.github.io/tp/UserGuide.html"
            Press the 'Copy URL' button to copy the link to your clipboard.""";

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
     * Formats the {@link Person} for display to the user.
     */
    public static String formatPerson(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail());
        builder.append("; Roles: ");
        person.getRoles().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@link Event} for display to the user.
     */
    public static String formatEvent(Event event) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event Name: ")
                .append(event.getName())
                .append("; Sport: ")
                .append(event.getSport())
                .append("; Teams: ")
                .append(event.getTeams().getKey())
                .append(", ")
                .append(event.getTeams().getValue())
                .append("; Venue: ")
                .append(event.getVenue())
                .append("; Date and Time: ")
                .append(event.getDateTimeDisplayString());
        builder.append("; Participants: ");
        event.getParticipants().stream().map(Messages::formatPerson).forEach(builder::append);
        return builder.toString();
    }
}
