package seedu.ddd.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.ddd.logic.parser.Prefix;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Event;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n\n%1$s";
    public static final String MESSAGE_INVALID_FLAGS = "No flag or invalid flag specified! Use -c OR -v OR -e %1$s";
    public static final String MESSAGE_INVALID_PREFIX = "Not allowed to filter by %s when you specify %s";
    public static final String MESSAGE_INVALID_PREFIX_EMPTY_INPUT = "Not allowed to have an empty input after %s";
    public static final String MESSAGE_INVALID_CONTACT_ID = "Invalid contact ID! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid.";
    public static final String MESSAGE_DISPLAYED_INDEX_TOO_LARGE = "The index provided goes out of bounds.";
    public static final String MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX = "The client index provided is invalid.";
    public static final String MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX = "The vendor index provided is invalid.";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contact(s) listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d event(s) listed!";
    public static final String MESSAGE_CLIENTS_LISTED_OVERVIEW = "%1$d client(s) listed!";
    public static final String MESSAGE_VENDORS_LISTED_OVERVIEW = "%1$d vendor(s) listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_UNKNOWN_ITEM = "Unknown item displayed in list.";
    public static final String MESSAGE_DEPENDENT_EVENT =
            "Unable to delete contact!\n Event(s) %1$s depend on the contact you are trying to delete.";
    public static final String MESSAGE_EXCLUSIVE_FLAGS = "Only 1 of the following flags can be specified: ";
    public static final String MESSAGE_NOT_DISPLAYING_CONTACTS = "The displayed list does not contain contacts.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(", ", duplicateFields);
    }

    /**
     * Returns an error message indicating the exclusive prefixes.
     */
    public static String getErrorMessageForExclusiveFlags(Prefix... exclusiveFlags) {
        assert exclusiveFlags.length > 0;

        Set<String> exclusiveFields =
                Stream.of(exclusiveFlags).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_EXCLUSIVE_FLAGS + String.join(", ", exclusiveFields);
    }
    /**
     * Returns an error message indicating which fields are incompatible with the selected flag
     */
    public static String getErrorMessageForPrefix(Prefix prefix, Prefix flag) {
        return String.format(MESSAGE_INVALID_PREFIX, prefix.toString(), flag.toString());
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

    /**
     * Formats the {@code event} for display to the user.
     */
    public static String format(Event event) {
        final StringBuilder builder = new StringBuilder();
        // Add client names, separating with a comma
        String clients = event.getClients().stream()
                .map(client -> client.getName() != null ? client.getName().toString() : "Unknown Client")
                .collect(Collectors.joining(", "));

        // Add vendor names, separating with a comma
        String vendors = event.getVendors().stream()
                .map(vendor -> vendor.getName() != null ? vendor.getName().toString() : "Unknown Vendor")
                .collect(Collectors.joining(", "));

        builder.append(event.getEventId())
                .append("; Description: ")
                .append(event.getDescription())
                .append("; Clients: ")
                .append(clients)
                .append("; Vendors: ")
                .append(vendors);

        return builder.toString();
    }
}
