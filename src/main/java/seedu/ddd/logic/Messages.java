package seedu.ddd.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ddd.logic.parser.Prefix;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_TYPE = "Invalid contact type! Use -c or -v to specify adding "
            + "of client or vendor contact.";
    public static final String MESSAGE_MULTIPLE_CONTACT_TYPES = "Multiple contact types specified. Use -c OR -v to "
            + "specify adding of client OR vendor contact.";
    public static final String MESSAGE_INVALID_CONTACT_ID = "Invalid contact ID! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX = "The client index provided is invalid";
    public static final String MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX = "The vendor index provided is invalid";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_CLIENTS_LISTED_OVERVIEW = "%1$d clients listed!";
    public static final String MESSAGE_VENDORS_LISTED_OVERVIEW = "%1$d vendors listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EXCLUSIVE_FIELDS = "Only 1 of the following arguments can be specified";

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
     * Returns an error message indicating the exclusive prefixes.
     */
    public static String getErrorMessageForExclusivePrefixes(Prefix... exclusivePrefixes) {
        assert exclusivePrefixes.length > 0;

        Set<String> exclusiveFields =
                Stream.of(exclusivePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_EXCLUSIVE_FIELDS + String.join(" ", exclusiveFields);
    }

    /**
     * Formats the {@code contact} for display to the user.
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
                .append(client.getDate())
                .append("; Tags: ");
        client.getTags().forEach(builder::append);
        builder.append("; ID: ")
                .append(client.getId());
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
        builder.append("; ID: ")
                .append(vendor.getId());
        return builder.toString();
    }
}
