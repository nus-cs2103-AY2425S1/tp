package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.company.Company;
import seedu.address.model.job.Job;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DISPLAYED_INDEX = "The index provided is out of range of the displayed"
            + " list";
    public static final String MESSAGE_INVALID_ENTITY = "Invalid entity '%1$s'. Valid entities are 'company', "
            + "'contact', 'job', or 'all'";
    public static final String MESSAGE_OPERATION_NOT_ALLOWED = "Command '%1$s' is not allowed on entity '%2$s'.";
    public static final String MESSAGE_MISSING_INDEX = "The index is missing";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_EMAIL = "Invalid email address. Please enter a valid email";
    public static final String MESSAGE_INVALID_NAME = "Invalid name. Please enter a valid name "
            + "using alphabetical characters and common punctuation.";
    public static final String MESSAGE_INVALID_PHONE = "Invalid phone number. Please enter a number "
            + "between 8 and 15 digits.";
    public static final String MESSAGE_INVALID_ROLE = "Invalid role. Please enter a valid job "
        + "title for the candidate.";
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
                .append("; Role: ")
                .append(person.getRole())
                .append("; Skills: ");
        person.getSkills().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code job} for display to the user.
     */
    public static String format(Job job) {
        final StringBuilder builder = new StringBuilder();
        builder.append(job.getName())
                .append("; Company: ")
                .append(job.getCompany())
                .append("; Salary: ")
                .append(job.getSalary())
                .append("; Description: ")
                .append(job.getDescription())
                .append("; Requirements: ");
        job.getRequirements().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code company} for display to the user.
     */
    public static String format(Company company) {
        final StringBuilder builder = new StringBuilder();
        builder.append(company.getName())
                .append("; Phone: ")
                .append(company.getPhone())
                .append("; Address: ")
                .append(company.getAddress())
                .append("; Billing Date: ")
                .append(company.getBillingDate());
        return builder.toString();
    }


}
