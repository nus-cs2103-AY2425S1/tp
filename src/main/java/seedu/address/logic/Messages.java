package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Supplier;

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
        builder.append("\n")
                .append("Name: ")
                .append(person.getName()).append("\n")
                .append("Phone: ")
                .append(person.getPhone()).append("\n")
                .append("Email: ")
                .append(person.getEmail()).append("\n")
                .append("Address: ")
                .append(person.getAddress()).append("\n")
                .append("Tags: ")
                .append("\n");
        person.getTags().forEach(builder::append);
        // Check if the person is a Customer and append the information
        if (person instanceof Customer) {
            Customer customer = (Customer) person;
            builder.append("\n").append("Information: ").append(customer.getInformation()).append("\n");
        }

        // Check if the person is a Supplier and append the ingredients supplied
        if (person instanceof Supplier) {
            Supplier supplier = (Supplier) person;
            builder.append("\n").append("Ingredients Supplied: ")
                    .append("\n")
                    .append(supplier.getIngredientsSupplied()).append("\n");
        }
        return builder.toString();
    }

}
