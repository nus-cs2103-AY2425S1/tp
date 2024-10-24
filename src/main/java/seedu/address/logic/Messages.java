package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_NO_LATEST_COMMAND = "There is no latest command to undo";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "One or more of the index provided is invalid";
    public static final String MESSAGE_INVALID_PARAMETER = "One or more of the parameters provided is invalid";
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
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Date of birth: ")
                .append(person.getDateOfBirth())
                .append("; Priority: ")
                .append(person.getPriority())
                .append("; Income: ")
                .append(person.getIncome())
                .append("; Family size: ")
                .append(person.getFamilySize());

        String remark = person.getRemark().value;
        if (!remark.isEmpty()) {
            builder.append("; Remark: ").append(remark);
        }

        Appointment appointment = person.getAppointment();
        if (appointment != null) {
            builder.append("; Appointment: ").append(appointment);
        }

        Set<Tag> tags = person.getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        return builder.toString();
    }
}
