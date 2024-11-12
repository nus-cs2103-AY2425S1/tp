package seedu.address.logic;

import java.time.format.DateTimeFormatter;
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
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_PERSONS_FOUND_MAJOR = "Found %d people that are taking %s";
    public static final String MESSAGE_PERSON_FOUND_MAJOR = "Found 1 person that is taking %s";
    public static final String MESSAGE_PERSON_FOUND_UNIVERSITY = "Found 1 person in %s";
    public static final String MESSAGE_PERSONS_FOUND_UNIVERSITY = "Found %d people that are in %s";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_PERSONS_FOUND_INTEREST = "Found %d people that have similar interest";
    public static final String MESSAGE_PERSON_FOUND_INTEREST = "Found 1 person that have similar interest";
    public static final String MESSAGE_PERSON_FOUND_WORKEXP = "Found 1 person who has worked or is working %s";
    public static final String MESSAGE_PERSONS_FOUND_WORKEXP = "Found %d people who have worked or are working %s";


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
                .append("; Work Experience: ")
                .append(person.getWorkExp())
                .append("; Tags: ")
                .append(person.getTags())
                .append("; University: ")
                .append(person.getUniversity())
                .append("; Major: ")
                .append(person.getMajor())
                .append("; Interests: ")
                .append(person.getInterests())
                .append("; Birthday: ")
                .append(person.getBirthday().value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return builder.toString();
    }
}
