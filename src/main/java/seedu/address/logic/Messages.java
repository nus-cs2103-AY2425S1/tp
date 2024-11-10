package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "Please enter an index/indices "
            + "within the size of the list. (Your list has %1$d entries.)";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS = "Multiple values specified for the following single-valued "
            + "field(s): ";

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
                .append(";\n Email: ")
                .append(person.getEmail())
                .append(";\n Gender: ")
                .append(person.getGender())
                .append(";\n Age: ")
                .append(person.getAge())
                .append(";\n Details: ")
                .append(person.getDetail())
                .append(";\n Study Groups: ");
        person.getStudyGroupTags().forEach(builder::append);
        return builder.toString();
    }

}
