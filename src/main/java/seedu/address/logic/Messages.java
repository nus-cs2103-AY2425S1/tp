package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_GUEST_DISPLAYED_INDEX =
            "The guest index provided exceeds the number of displayed guests!";
    public static final String MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX =
            "The vendor index provided exceeds the number of displayed vendors!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_TOTAL_GUEST = "Number of guests: %d (%d pending, %d coming, %d not coming)\n";
    public static final String MESSAGE_TOTAL_VENDOR = "Number of vendors: %d";

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
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code guest} for display to the user.
     */
    public static String format(Guest guest) {
        final StringBuilder builder = new StringBuilder();
        builder.append(guest.getName())
                .append("; Phone: ")
                .append(guest.getPhone())
                .append("; Email: ")
                .append(guest.getEmail())
                .append("; Address: ")
                .append(guest.getAddress())
                .append("; RSVP: ")
                .append(guest.getRsvp())
                .append("; Relation: ")
                .append(guest.getRelation())
                .append("; Tags: ");
        guest.getTags().forEach(builder::append);
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
                .append("; Company: ")
                .append(vendor.getCompany())
                .append("; Budget: $")
                .append(vendor.getBudget())
                .append("; Tags: ");
        vendor.getTags().forEach(builder::append);
        return builder.toString();
    }

    public static String getSuccessMessageWithStats(String commandSuccess, int[] guestCounts, int vendorCount) {
        assert guestCounts.length == 4;

        int guestCount = guestCounts[0];
        int guestsPending = guestCounts[1];
        int guestsComing = guestCounts[2];
        int guestsNotComing = guestCounts[3];

        String guestMessage = String.format(MESSAGE_TOTAL_GUEST,
                guestCount, guestsPending, guestsComing, guestsNotComing);

        String vendorMessage = String.format(MESSAGE_TOTAL_VENDOR, vendorCount);
        return commandSuccess + guestMessage + vendorMessage;
    }
}
