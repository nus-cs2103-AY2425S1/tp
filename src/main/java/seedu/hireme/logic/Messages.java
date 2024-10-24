package seedu.hireme.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.hireme.logic.parser.Prefix;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_INTERNSHIP_APPLICATION_DISPLAYED_INDEX =
            "The internship application index provided is invalid";
    public static final String MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW = "%1$d internship applications listed!";
    public static final String MESSAGE_INTERNSHIP_APPLICATIONS_SORTED_OVERVIEW = "Internship applications list "
            + "has been sorted!";
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
     * Formats the {@code internship} for display to the user.
     */
    public static String format(InternshipApplication internship) {
        final StringBuilder builder = new StringBuilder();
        builder.append(internship.getCompany())
                .append("; Role: ")
                .append(internship.getRole())
                .append("; Date: ")
                .append(internship.getDateOfApplication());
        return builder.toString();
    }

}
