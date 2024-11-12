package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEXES =
            "One or more of the indexes provided are invalid";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX =
            "The appointment index provided is invalid";
    public static final String MESSAGE_INVALID_PARAMETERS = "One or more of the parameters provided are invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    public static final String MESSAGE_INVALID_DATE_FORMAT =
            "Dates should be in yyyy-MM-dd format and represent a valid date";
    public static final String MESSAGE_INVALID_TIME_FORMAT =
            "Times should be in HH:mm format and represent a valid time";
    public static final String MESSAGE_CONFLICTING_APPOINTMENTS = "Conflicting appointments found:\n%s";

    public static final String MESSAGE_NO_SCHEMES_AVAILABLE = "There are no schemes available for this person";

    public static final String MESSAGE_INVALID_SCHEMES_DISPLAYED_INDEX = "The scheme index provided is invalid";

    public static final String MESSAGE_DUPLICATE_SCHEME = "This scheme already exists in the person";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_DELETE_SCHEME_PERSON_SUCCESS = "The following schemes have been "
            + "deleted from %1$s:\n";

    public static final String MESSAGE_ADD_SCHEME_PERSON_SUCCESS = "The following scheme has been added to %1$s:\n"
            + "%2$s";

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
     * <p>
     * The {@code updatedAt} field is not included due to its nondeterministic nature.
     * It is impossible to predict the value of the {@code updatedAt} field.
     * This ensures that unit testing for {@link EditCommand} is not affected.
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

        Set<Tag> tags = person.getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        return builder.toString();
    }

    /**
     * Formats a list of {@code appointments} for display to the user.
     */
    public static String format(List<Appointment> appointments) {
        final StringBuilder builder = new StringBuilder();
        for (Appointment appointment : appointments) {
            builder.append("Name: ")
                    .append(appointment.name())
                    .append("; Date: ")
                    .append(appointment.date())
                    .append("; From: ")
                    .append(appointment.startTime())
                    .append("; To: ")
                    .append(appointment.endTime())
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Formats the {@code appointment} for display to the user.
     */
    public static String format(Appointment appointment) {
        return String.format("Date: %s\nTime: %s – %s",
                appointment.getFormattedDate(),
                appointment.getFormattedStartTime(),
                appointment.getFormattedEndTime());
    }
}
