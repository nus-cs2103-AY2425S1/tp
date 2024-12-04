package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command! Type 'help' to see the list of commands";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_INPUT = "Invalid input! \n%1$s";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_NON_POSITIVE_INDEX = "Index must be a positive number!\n";


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
        builder.append(person.getName());

        if (person instanceof Student) {
            Student student = (Student) person;
            builder.append("; Student ID: ").append(student.getStudentId());
        } else if (person instanceof Company) {
            Company company = (Company) person;
            builder.append("; Industry: ").append(company.getIndustry());
        }

        builder.append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(tag -> builder.append(tag).append(" "));

        return builder.toString().trim();
    }

    /**
     * Formats the {@code persons} for display to the user.
     */
    public static String format(List<Person> persons) {
        final StringBuilder builder = new StringBuilder();
        int count = 1;
        for (Person person : persons) {
            builder.append("\n")
                    .append(count)
                    .append(". ")
                    .append(format(person));
            count++;
        }

        return builder.toString();
    }
}
