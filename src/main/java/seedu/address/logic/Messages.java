package seedu.address.logic;

import java.util.List;
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
    public static final String MESSAGE_INVALID_PERSONS_INDEX = "One or more contact indexes are invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_SCHEDULE_LISTED_OVERVIEW = "%1$s to %2$s schedule listed!";

    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_GENERIC_INDEX_OUT_OF_BOUNDS =
            "Index provided is out of bounds\n%1$s";

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
                .append("; Tags: ")
                .append("; favourite: ")
                .append(person.getFavourite());
        person.getTags().forEach(builder::append);
        return builder.toString();
    }
    /**
     * @param people
     * @return a String Format of all the Person Objects in the people list
     */
    public static String format(List<Person> people) {
        final StringBuilder builder = new StringBuilder();
        for (Person person: people) {
            builder.append(person.getName())
                    .append("; Phone: ")
                    .append(person.getPhone())
                    .append("; Email: ")
                    .append(person.getEmail())
                    .append("; Address: ")
                    .append(person.getAddress())
                    .append("; Tags: ")
                    .append("; favourite: ")
                    .append(person.getFavourite());
            person.getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
