package seedu.ddd.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ddd.logic.parser.Prefix;
import seedu.ddd.model.person.Client;
import seedu.ddd.model.person.Contact;
import seedu.ddd.model.person.Vendor;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_TYPE = "Invalid contact type! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    // TODO: determine if "contact" should also be added here
    public static final String MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX = "The client index provided is invalid";
    public static final String MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX = "The vendor index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_CLIENTS_LISTED_OVERVIEW = "%1$d clients listed!";
    public static final String MESSAGE_VENDORS_LISTED_OVERVIEW = "%1$d vendors listed!";
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
     * TODO: add docs
     */
    public static String format(Contact contact) {
        return contact instanceof Client
            ? format((Client) contact)
            : format((Vendor) contact);
    }

    /**
     * Formats the {@code client} for display to the user.
     */
    public static String format(Client client) {
        final StringBuilder builder = new StringBuilder();
        builder.append(client.getName())
                .append("; Phone: ")
                .append(client.getPhone())
                .append("; Email: ")
                .append(client.getEmail())
                .append("; Address: ")
                .append(client.getAddress())
                .append("; Date: ")
                //.append(client.getDate())
                .append("; Tags: ");
        client.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code vendor} for display to the user.
     */
    public static String format(Vendor vendor) {
        final StringBuilder builder = new StringBuilder();
        builder.append(vendor.getName())
                .append("; Phone: ")
                .append(vendor.getPhone())
                .append("; Email: ")
                .append(vendor.getEmail())
                .append("; Address: ")
                .append(vendor.getAddress())
                .append("; Service: ")
                .append(vendor.getService())
                .append("; Tags: ");
        vendor.getTags().forEach(builder::append);
        return builder.toString();
    }
}
