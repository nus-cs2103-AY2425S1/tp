package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The supplier index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_SUPPLIER_SORTED_OVERVIEW = "%1$d suppliers sorted by %2$s in %3$s order!";
    public static final String MESSAGE_DELIVERY_SORTED_OVERVIEW = "%1$d deliveries sorted by %2$s in %3$s order!";
    public static final String MESSAGE_DELIVERIES_LISTED_OVERVIEW = "%1$d deliveries listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX = "The delivery index provided is invalid";
    public static final String MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX = "The supplier index provided is invalid";

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
                .append("; Company: ")
                .append(person.getCompany())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        builder.append("; Products ");
        person.getProducts().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code Delivery} for display to the user.
     */
    public static String format(Delivery delivery) {
        final StringBuilder builder = new StringBuilder();
        builder.append(delivery.getDeliverySender().getName())
                .append("; Date & time: ")
                .append(delivery.getDeliveryDate())
                .append("; Product: ")
                .append(delivery.getDeliveryProduct())
                .append("; Quantity: ")
                .append(delivery.getDeliveryQuantity())
                .append("; Cost: ")
                .append(delivery.getDeliveryCost())
                .append("; Status: ")
                .append(delivery.getDeliveryStatus());
        return builder.toString();
    }

}
