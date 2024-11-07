package seedu.sellsavvy.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.sellsavvy.logic.parser.Prefix;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.customer.Customer;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The customer index provided is invalid!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d customers listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid!";
    public static final String MESSAGE_ORDERLIST_DOES_NOT_EXIST =
            "No orders displayed in displayed list. Please use listOrder show a customer's order list first!";
    public static final String MESSAGE_SIMILAR_NAME_WARNING = "Note: "
            + "A customer with similar name already exists in the address book, "
            + "verify if this is a mistake.\n";

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
     * Formats the {@code customer} for display to the user.
     */
    public static String format(Customer customer) {
        final StringBuilder builder = new StringBuilder();
        builder.append(customer.getName())
                .append("; Phone: ")
                .append(customer.getPhone())
                .append("; Email: ")
                .append(customer.getEmail())
                .append("; Address: ")
                .append(customer.getAddress())
                .append("; Tags: ");
        customer.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code order} for display to the user.
     */
    public static String format(Order order) {
        final StringBuilder builder = new StringBuilder();
        builder.append(order.getItem() + "\n")
                .append("Delivery by: ")
                .append(order.getDate())
                .append("; Quantity: ")
                .append(order.getQuantity());
        return builder.toString();
    }
}
