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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The patient index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d patient(s) listed!";
    public static final String MESSAGE_APPOINTMENTS_LISTED_OVERVIEW = "%1$d appointment(s) on %2$s listed";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String WARD_SPECIAL_CHARACTER = "Warning! Ward field includes special characters.\n";
    public static final String ID_SPECIAL_CHARACTER = "Warning! Id field includes special characters.\n";
    public static final String WARD_ID_SPECIAL_CHARACTER = "Warning! Ward & Id fields include special characters.\n";

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
        assert person != null;
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(person.getName())
                .append("\n ID: ")
                .append(person.getId())
                .append("\n Ward: ")
                .append(person.getWard())
                .append("\n Diagnosis: ")
                .append(person.getDiagnosis().toString().isEmpty() ? "-" : person.getDiagnosis())
                .append("\n Medication: ")
                .append(person.getMedication().toString().isEmpty() ? "-" : person.getMedication())
                .append("\n Notes: ")
                .append(person.getNotes().toString().isEmpty() ? "-" : person.getNotes())
                .append("\n Appointment: ")
                .append(person.getAppointment() == null ? "-" : person.getAppointment().toString());

        return builder.toString();
    }

}
