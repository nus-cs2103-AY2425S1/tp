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

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    // List Command Error Message
    public static final String LIST_MESSAGE_INVALID_COMMAND = "Please ensure your command is valid!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString)
                .collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     * Only provides minimal information required.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        addName(builder, person);
        addPhone(builder, person);
        addTags(builder, person);
        addDateOfLastVisit(builder, person);

        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     * Provides all the information about the user.
     */
    public static String formatFull(Person person) {
        final StringBuilder builder = new StringBuilder();
        addName(builder, person);
        addPhone(builder, person);
        addEmail(builder, person);
        addAddress(builder, person);
        addTags(builder, person);
        addDateOfLastVisit(builder, person);
        addEmergencyContact(builder, person);
        addRemark(builder, person);
        return builder.toString();
    }

    private static void addName(StringBuilder sb, Person person) {
        sb.append(person.getName());
    }

    private static void addPhone(StringBuilder sb, Person person) {
        sb.append("; Phone: ").append(person.getPhone());
    }

    private static void addDateOfLastVisit(StringBuilder sb, Person person) {
        if (!person.hasDateOfLastVisit()) {
            return;
        }
        sb.append("; Last visit: ").append(person.getDateOfLastVisit().get());
    }

    private static void addEmail(StringBuilder sb, Person person) {
        if (!person.hasEmail()) {
            return;
        }
        sb.append("; Email: ").append(person.getEmail().get());
    }

    private static void addAddress(StringBuilder sb, Person person) {
        if (!person.hasAddress()) {
            return;
        }
        sb.append("; Address: ").append(person.getAddress().get());
    }

    private static void addTags(StringBuilder sb, Person person) {
        if (person.getTags().isEmpty()) {
            return;
        }
        sb.append("; Tags: ");
        person.getTags().forEach(sb::append);
    }

    private static void addEmergencyContact(StringBuilder sb, Person person) {
        if (!person.hasEmergencyContact()) {
            return;
        }
        sb.append("; Emergency Contact: ").append(person.getEmergencyContact().get());
    }

    private static void addRemark(StringBuilder sb, Person person) {
        if (!person.hasRemark()) {
            return;
        }
        sb.append("; Remark: ").append(person.getRemark().value);
    }

}
