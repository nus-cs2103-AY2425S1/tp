package seedu.address.logic;

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
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!";
    public static final String MESSAGE_CHECK_OUT_HELP =
            "You may use the help command to see all commands available!";
    public static final String MESSAGE_EMPTY_FIND_KEYWORD = "Find keyword(s) cannot be empty!";
    public static final String MESSAGE_FIND_KEYWORD_CONTAINS_WHITESPACE =
            "Find keyword(s) cannot contain whitespace(s)!";
    public static final String MESSAGE_MISSING_INDEX = "Missing index for command!";
    public static final String MESSAGE_PERSON_INDEX_OUT_OF_BOUND =
            "Error: The person index %1$d provided is out of bounds!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d person(s) found with condition: %2$s";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_HELP_KEYWORD = "Unknown search keyword for help command: %1$s\n"
            + "Input \"help\" keyword only to get full list of command instructions";

    public static final String MESSAGE_CHAINED_FIND_PREFIX = "In the current filtered list: ";
    public static final String MESSAGE_UNEXPECTED_PREAMBLE = "Unexpected text found without prefix.";
    public static final String MESSAGE_UNEXPECTED_PREFIX = "Unexpected prefix found: %1$s";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0 : "No duplicate prefix found when prompting error message to user";

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix :: toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Returns an error message indicating the unexpected prefixes.
     */
    public static String getErrorMessageForUnexpectedPrefixes(Prefix... unexpectedPrefixes) {
        assert unexpectedPrefixes.length > 0 : "No unexpected prefix found when prompting error message to user";

        Set<String> unexpectedFields =
                Stream.of(unexpectedPrefixes).map(Prefix :: toString).collect(Collectors.toSet());

        return String.format(MESSAGE_UNEXPECTED_PREFIX, String.join(" ", unexpectedFields));
    }

    /**
     * Returns a formatted error message with the given error message and the proper usage.
     */
    public static String getErrorMessageWithUsage(String errorMessage, String usage) {
        return "Error: " + errorMessage + "\nUsage:\n" + usage;
    }
    /**
     * Returns an ordered list string representation of items in the given array.
     */
    public static String getOrderedListString(Object[] array) {
        StringBuilder resultSB = new StringBuilder();
        for (int i = 1; i <= array.length; i++) {
            resultSB.append(String.format("%d. %s", i, array[i - 1].toString()));
            if (i < array.length) {
                resultSB.append("\n\n");
            }
        }
        return resultSB.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName());

        // Only display Phone: if present
        person.getPhone().ifPresent(phone -> builder.append("; Phone: ")
                .append(person.getPhone().map(Object::toString).orElse("")));

        // Only display Email: if present
        person.getEmail().ifPresent(email -> builder.append("; Email: ")
                .append(person.getEmail().map(Object::toString).orElse("")));

        // Only display Address: if present
        person.getAddress().ifPresent(address -> builder.append("; Address: ")
                .append(person.getAddress().map(Object::toString).orElse("")));

        // Only display Tags: if there are > 0 tags
        Set<Tag> tags = person.getTags();
        if (tags.size() > 0) {
            builder.append("; Tags: ");
            person.getTags().forEach(builder :: append);
        }

        // Only display Roles: if there are > 0 roles
        if (!person.hasEmptyModuleRoleMap()) {
            builder.append("; Roles: ");
            String moduleRoleMapData = person.getModuleRoleMap().getData().stream()
                    .map(Object::toString).collect(Collectors.joining(", "));
            builder.append(moduleRoleMapData);
        }

        // Only display Description: if present
        person.getDescription().filter(value -> !value.isBlank())
            .ifPresent(description -> builder.append("; Description: ")
            .append(person.getDescription().map(Object::toString).orElse("")));

        return builder.toString();
    }

}
