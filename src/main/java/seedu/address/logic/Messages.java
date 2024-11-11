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
        builder.append("Name: ").append(person.getName());
        if (!person.getPhone().isEmpty()) {
            builder.append("; Phone: ")
                    .append(person.getPhone());
        }

        if (!person.getEmail().isEmpty()) {
            builder.append("; Email: ")
                    .append(person.getEmail());
        }

        if (!person.getAddress().isEmpty()) {
            builder.append("; Location: ")
                    .append(person.getAddress());
        }

        if (!person.getRemark().isEmpty()) {
            builder.append("; Remark: ")
                    .append(person.getRemark());
        }

        if (!person.getTags().isEmpty()) {
            builder.append("; Tags: ");
            person.getTags().forEach(builder::append);
        }

        return builder.toString();
    }

    /**
     * Formats the list of {@code people} for display to the user.
     */
    public static String format(List<Person> people) {
        return people.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n", "\n", ""));
    }
}
