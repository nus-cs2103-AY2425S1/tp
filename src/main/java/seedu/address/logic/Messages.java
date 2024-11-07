package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.event.Event;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid!";
    public static final String MESSAGE_MISSING_PERSON = "%s not found in TalentHub!!";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";

    public static final String MESSAGE_PERSON_VIEWED_OVERVIEW = "%s shown!";
    public static final String MESSAGE_VIEWED_PERSON_NOT_FOUND = "Name not found in TalentHub!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_CONFIRM_COMMAND = "Please input Y/N to confirm your operation!";
    public static final String MESSAGE_INVALID_TIME_FORMAT = "Incorrect time field! "
            + "Please follow the format: "
            + "\"t/YYYY-MM-DD HH:mm to YYYY-MM-DD HH:mm\"";
    public static final String MESSAGE_INVALID_CELEBRITY_FORMAT = "Celebrity name cannot be blank";
    public static final String MESSAGE_INVALID_EVENT_CONTACT_FORMAT = "Contact name cannot be blank";
    public static final String MESSAGE_INVALID_NAME = "Names must be alphanumeric";

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
                .append(person.getEmail().isPresent() ? person.getEmail().get() : "[To be added]")
                .append("; Address: ")
                .append(person.getAddress().isPresent() ? person.getAddress().get() : "[To be added]")
                .append("; Tags: ");

        if (person.getTags().isEmpty()) {
            builder.append("[To be added]");
        } else {
            person.getTags().forEach(builder::append);
        }

        return builder.toString();
    }

    /**
     * Formats the {@code event} for display to the user.
     */
    public static String eventFormat(Event event) {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getName())
                .append("; Time: ")
                .append(event.getTime())
                .append("; Venue: ")
                .append(event.getVenue().map(Venue::toString).orElse("[To be added]"))
                .append("; Celebrity: ")
                .append(event.getCelebrityName())
                .append("; Contacts: ")
                .append(event.getContacts().isEmpty() ? "[To be added]" : event.getContactsString() );
        return builder.toString();
    }

}
