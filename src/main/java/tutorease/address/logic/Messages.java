package tutorease.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorease.address.logic.parser.Prefix;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_LESSONS_LISTED_OVERVIEW = "%1$d lessons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_NO_LESSONS_FOUND = "No lessons found!";
    public static final String MISSING_PREFIX = "%s prefix is missing!\n" + "%s";
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
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code lesson} for display to the user.
     *
     * @param lesson The lesson to be formatted.
     * @return The formatted lesson.
     */
    public static String formatLesson(Lesson lesson) {
        final StringBuilder builder = new StringBuilder();
        builder.append(lesson);
        return builder.toString();
    }

}
