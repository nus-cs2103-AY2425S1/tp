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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person ID provided is invalid"
            + " Please check and enter a new one";
    public static final String MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX = "The patient ID provided is invalid"
            + " Please check and enter a new one";
    public static final String MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX = "The doctor ID provided is invalid"
            + " Please check and enter a new one";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d person listed! "
            + "Key in [list] to view all patients";
    public static final String MESSAGE_INVALID_ID = "Invalid ID entered! Check the ID that you have entered! "
            + "Make sure the ID is valid!";
    public static final String MESSAGE_EMPTY_REMARK = "The remark you have entered is empty! "
            + "Please enter a valid input!";
    public static final String MESSAGE_INVALID_NAME = "Invalid name entered! "
            + "Check the name that you want to search id for!\n"
            + "Key in [list] to view all patients";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_GET_ID = "The id of the person that you are finding is: %1$d";
    public static final String MESSAGE_MULTIPLE_PERSONS_WITH_THE_SAME_NAME = "%1$d persons listed "
            + "that suits your keyword!\n"
            + "Enter more specific name keywords to retrieve the id of the person";
    public static final String MESSAGE_MULTIPLE_PATIENT_ID =
            "You have entered two patient IDs, which is invalid.";
    public static final String MESSAGE_MULTIPLE_DOCTOR_ID =
            "You have entered two doctor IDs, which is invalid.";
    public static final String MESSAGE_MIXED_SEQUENCE_ID =
            "You have entered a doctor ID and a patient ID in the wrong sequence.";
    public static final String MESSAGE_COMPLETED_APPOINTMENT =
            "The appointment is completed. Check the appointment that you want to mark!";

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
                .append(";");
        return builder.toString();
    }
}


