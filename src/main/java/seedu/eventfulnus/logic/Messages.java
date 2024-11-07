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

            These are the available commands:

            help - Display this help page.
            Usage: help

            add - Add a Person to the list. They must have a name, phone number, email and an event tied to them.
            Usage: add n/<name> p/<phone number> m/<email> {r/<role1>} {r/<role2>} {r/<role3>} ...

            You can find a list of the available roles below in the examples,\s
            and you can add multiple roles to a person.

            You can find a list of the available faculties, sports, branches, positions, and volunteer roles\s
            at the bottom of this help page.

            Note: The hyphens between the fields are NOT necessary for the program to work, they are there for\s
            readability purposes.

            Examples:
            1. add ... r/athlete - <faculty> - <sport1>, {sport2}, {sport3}...
            2. add ... r/referee - <faculty> - <sport1>, {sport2}, {sport3}...
            3. add ... r/committee - <branch> - <position>
            4. add ... r/committee - Sports - <position> - <faculty>
            5. add ... r/sponsor - <company name>
            6. add ... r/volunteer - <volunteerRole>

            clear - Delete all entries in the list. But be careful, the deleted entries are gone forever.
            Usage: clear

            find - Show all Persons containing a case-insensitive keyword in their name, phone, email, or roles.
            Usage: find <keyword1> {<keyword2>} {<keyword3>} ...

            findevent - Show all Events containing a case-insensitive keyword in their name, sport,\s
            venue, or participants.
            Usage: findevent <keyword1> {<keyword2>} {<keyword3>} ...

            list - List all Persons registered to EventfulNUS.
            Usage: list

            listevents - List all Events registered to EventfulNUS.
            Usage: listevents

            edit - Update a Person's information.
            Usage: edit <id> {n/<name>} {p/<phone number>} {m/<email>} {r/<role1>} {r/<role2>}\s
            {r/<role3>} ...

            WARNING: You must specify at least one field to update. Otherwise, an error will be shown.
            WARNING: Editing the event will remove the person from the previous event and add them to the new event.
            WARNING: These edits are OVERWRITING changes, not additive. Be careful when editing.
            Note: You can update multiple fields at once, and you can add multiple roles to a person.

            delete - Delete a Person with the given ID.
            Usage: delete <id>

            exit - Exit the program.
            Usage: exit

            Input data is automatically saved when you exit EventfulNUS, and will be loaded the next time you run it.

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
        event.getParticipants().forEach(builder::append);
        return builder.toString();
    }
}
