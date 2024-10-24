package seedu.address.logic;

import static seedu.address.model.person.Person.DEFAULT_TAG_PENDING;
import static seedu.address.model.person.Person.TAG_HIRED;
import static seedu.address.model.person.Person.TAG_REJECTED;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

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
                .append("; Job: ")
                .append(person.getJob())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Skills: ");
        person.getSkills().forEach(builder::append);
        builder.append("; InterviewScore: ")
                .append(person.getInterviewScore())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats person to display just name, job and status to user
     */
    public static String formatStatus(Person p) {
        StringBuilder sb = new StringBuilder();

        sb.append(p.getName())
                .append("; Job: ")
                .append(p.getJob())
                .append("; Status: ");

        Set<Tag> tagList = p.getTags();

        // checks status of person p here
        if (tagList.contains(TAG_HIRED)) {
            sb.append("hired");
        } else if (tagList.contains(TAG_REJECTED)) {
            sb.append("rejected");
        } else if (tagList.contains(DEFAULT_TAG_PENDING)) {
            sb.append("pending");
        }
        return sb.toString();
    }

}
