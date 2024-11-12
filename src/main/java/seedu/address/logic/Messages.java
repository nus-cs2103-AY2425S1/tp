package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.patient.Patient;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_PATIENTS_LISTED_OVERVIEW = "There %1$s currently %2$d %3$s registered"
           + " in the ClinicConnect system";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_PATIENT_NRIC = "The patient with the NRIC %1$s does not exist in the "
            + "system.";
    public static final String MESSAGE_INVALID_APPT_DATETIME = "The appointment with the date and time"
            + " does not exist for this patient.";
    public static final String MESSAGE_INVALID_ALLERGY_TO_DELETE = "The allergy \"%1$s\" cannot be deleted as it is "
            + "not an allergy of this patient.";
    public static final String MESSAGE_INVALID_ALLERGY_TO_ADD = "The allergy \"%1$s\" already exists for this patient.";

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
     * Formats the {@code patient} for display to the user.
     */
    public static String format(Patient patient) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(patient.getName())
                .append("; NRIC: ")
                .append(patient.getNric())
                .append("; Sex: ")
                .append(patient.getSex())
                .append("; Birth Date: ")
                .append(patient.getBirthdate())
                .append("; Phone No.: ")
                .append(patient.getPhone());
        return builder.toString();
    }

}
