package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
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
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_HAS_CLASHES = "\nYou have %d other students with clashing schedule:\n%s";


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
                .append("; Schedule: ")
                .append(person.getSchedule())
                .append("; Subject: ")
                .append(person.getSubject())
                .append("; Fee: ")
                .append(person.getRate())
                .append("; Paid: ")
                .append(person.getPaid())
                .append("; Owed: ")
                .append(person.getOwedAmount());

        return builder.toString();
    }

    /**
     * Formats a list of persons into a single string representation based on the specified formatter function.
     * @param persons A list of {@link Person} objects to be formatted.
     * @param formatter A {@link Function} that takes a {@link Person} and returns a {@link String} representation
     *                  of that person. This allows for custom formatting of each person in the list.
     * @return A {@link String} that concatenates the formatted representations of all persons in the list.
     *          If the input list is empty, an empty string will be returned.
     */
    public static String listFormat(List<Person> persons, Function<Person, String> formatter) {
        StringBuilder result = new StringBuilder();

        for (Person person : persons) {
            result.append(formatter.apply(person));
        }

        return result.toString();
    }

    /**
     * Generates a warning message for schedule clashes.
     *
     * @param clashes The number of persons that have schedule clashes with the edited person.
     * @param clashingPersons A list of persons that have schedule clashes with the edited person.
     * @return A formatted warning message indicating the number of clashes and details of the clashing persons.
     */
    public static String getWarningMessageForClashes(long clashes, List<Person> clashingPersons) {
        return String.format(
                MESSAGE_HAS_CLASHES,
                clashes,
                listFormat(
                        clashingPersons,
                        person -> String.format("Name: %s | Schedule: %s\n", person.getName(), person.getSchedule()
                        )
                )
        );
    }

}
