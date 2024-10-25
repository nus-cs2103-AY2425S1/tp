package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Wedding;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS = "Multiple values specified "
            + "for the following single-valued field(s): ";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Message not implemented yet!";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

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
                .append("; Job: ")
                .append(person.getJob())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Wedding wedding) {
        final StringBuilder builder = new StringBuilder();
        builder.append(wedding.getWeddingName())
                .append("; Venue: ")
                .append(wedding.getVenue())
                .append("; Date: ")
                .append(wedding.getDate());
        return builder.toString();
    }

    /**
     * Formats the {@code person} for deletion.
     */
    public static String formatForDeletion(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(person.getName())
                .append("\nPhone: ").append(person.getPhone())
                .append("\nEmail: ").append(person.getEmail())
                .append("\nAddress: ").append(person.getAddress())
                .append("\nJob: ").append(person.getJob());
        return builder.toString();
    }

    /**
     * Returns the deletion message of {@code person}.
     */
    public static String getDeletionMessage(Person person) {
        return "Deleted Person:\n" + formatForDeletion(person);
    }

    /**
     * Formats the {@code tags} to display the set of tags in the appropriate format.
     */
    public static String tagSetToString(Set<Tag> tags) {
        return tags.stream().map(Tag::getTagName).collect(Collectors.joining(", "));
    }

    /**
     * Formats the {@code person} to display their tags.
     */
    public static String getName(Person person) {
        return person.getName().toString();
    }
}
