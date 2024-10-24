package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;


/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX = "The supplier index provided is invalid";
    public static final String MESSAGE_INVALID_STOCK_LEVEL = "Stock Level should be a non-negative integer.";
    public static final String MESSAGE_SUPPLIERS_LISTED_OVERVIEW = "%1$d suppliers listed!";
    public static final String MESSAGE_PRODUCTS_LISTED_OVERVIEW = "%1$d products listed!";

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
     * Formats the {@code supplier} for display to the user.
     */
    public static String format(Supplier supplier) {
        final StringBuilder builder = new StringBuilder();
        builder.append(supplier.getName())
                .append("; Phone: ")
                .append(supplier.getPhone())
                .append("; Email: ")
                .append(supplier.getEmail())
                .append("; Address: ")
                .append(supplier.getAddress())
                .append("; Tags: ");
        supplier.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code supplier} for display to the user.
     */
    public static String format(Product product) {
        final StringBuilder builder = new StringBuilder();
        builder.append(product.getName());
        return builder.toString();
    }
}
