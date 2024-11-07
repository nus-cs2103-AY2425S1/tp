package seedu.academyassist.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.academyassist.logic.parser.Prefix;
import seedu.academyassist.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_NO_STUDENT_FOUND = "No student found with the provided student ID";

    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "Number of students: %1$d";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_STUDENTS_FOUND_MATCHES = "%1$d student(s) found that matches the keywords.";
    public static final String MESSAGE_INVALID_NAME_FORMAT = "The name entered is invalid. "
            + "\nPlease make sure the name is between 1-100 characters and only contains alphabets and spaces.";
    public static final String MESSAGE_DUPLICATE_IC = "Another student with the same NRIC already exists "
            + "in the system.";

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
                .append("\nStudent ID: ")
                .append(person.getStudentId())
                .append("\nNRIC: ")
                .append(person.getIc())
                .append("\nYear group: ")
                .append(person.getYearGroup())
                .append("\nSubject(s) taken: ");
        person.getSubjects().forEach(s -> builder.append(s + " "));
        builder.append("\nUse the command `detail " + person.getStudentId() + "` to view more details");
        return builder.toString();
    }

}
