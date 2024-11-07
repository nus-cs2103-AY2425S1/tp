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

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command: ";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid!";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_HELP = """
            EventfulNUS allows you to manage a list of people and events, for easier event planning.
            These are the available commands:\n
            help - Display this help page.
            usage: help\n
            add - Add a Person to the list. They have a name, phone number, email and an event tied to them.
            usage: add n/<name> p/<phone number> e/<email> s/<subevent> {r/<role>}
            examples:
            1. add ... r/athlete - <faculty> - <sport>
            2. add ... r/committee - <branch> - <position>
            3. add ... r/committee - Sports - <position> - <faculty>
            4. add ... r/sponsor - <description>
            5. add ... r/volunteer - <volunteerRole>\n
            edit - Update the information in an entry of Person, via its ID.
            usage: edit <id> n/<name> p/<phone number> e/<email> s/<subevent> {r/<role>}\n
            delete - Delete a Person entry with the given ID.
            usage: delete <id>\n
            find - Show all entries containing a case-insensitive query in the Person's name, phone, email, or events.
            usage: find <query>\n
            addevent - Add an Event to the list. It has a name, a sport, a venue and some participants tied to it.
            usage: addevent n/<name> sp/<sport> v/<venue> pa/<participants>\n
            editevent - Update the information in an Event entry, via its ID.
            usage: editevent <id> n/<name> sp/<sport> v/<venue> pa/<participants>\n
            deleteevent - Delete an Event entry with the given ID.
            usage: deleteevent <id>\n
            findevent - Show all entries containing a case-insensitive query in the Event's name,
            sport, venue or participants.
            usage: findevent <query>\n
            clear - Delete all entries in the list. But be careful, the deleted entries are gone forever.
            usage: clear\n
            filter - Show all entries containing a case-insensitive keyword in the Person's roles.
            usage: filter <keyword>\n
            list - List all entries registered to EventfulNUS.
            usage: list\n
            exit - Exit the program.
            usage: exit\n
            Input data is automatically saved when you exit EventfulNUS, and will be loaded the next time you run it.\n
            Find out more about using EventfulNUS on our user guide at:
            https://ay2425s1-cs2103t-w14-4.github.io/tp/UserGuide.html
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
        return String.valueOf(event.getName());
    }
}
