package seedu.internbuddy.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.internbuddy.logic.parser.Prefix;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Company;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INDEX_EXCEEDS_SIZE = "Index is too large! (Should not exceed %d)";
    public static final String MESSAGE_COMPANIES_LISTED_OVERVIEW = "%1$d companies listed!";
    public static final String MESSAGE_COMPANY_LIST_EMPTY = "Company list is empty!";
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
     * Formats the {@code company} for display to the user.
     */
    public static String format(Company company) {
        final StringBuilder builder = new StringBuilder();
        builder.append(company.getName())
                .append("; Phone: ")
                .append(company.getPhone())
                .append("; Email: ")
                .append(company.getEmail())
                .append("; Address: ")
                .append(company.getAddress())
                .append("; Status: ")
                .append(company.getStatus())
                .append("; Tags: ");
        company.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code application} for display to the user.
     */
    public static String format(Application application) {
        return application.getName()
                + "; Description: "
                + application.getDescription()
                + "; App Status: "
                + application.getAppStatus();
    }

}
