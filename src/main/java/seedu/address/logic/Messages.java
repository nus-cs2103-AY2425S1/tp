package seedu.address.logic;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientTypes;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;

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
                Stream.of(duplicatePrefixes).map(Prefix::toString)
                        .sorted() // Sorting the strings
                        .collect(Collectors.toCollection(LinkedHashSet::new)); // To maintain the sorted order

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
     * Formats the {@code client} for display to the user.
     */
    public static String format(Client client) {
        final StringBuilder builder = new StringBuilder();
        builder.append(client instanceof Buyer ? ClientTypes.BUYER.toString() + "; "
                : ClientTypes.SELLER.toString() + "; ")
                .append(client.getName())
                .append("; Phone: ")
                .append(client.getPhone())
                .append("; Email: ")
                .append(client.getEmail());
        return builder.toString();
    }

    /**
     * Formats the {@code property} for display to the user.
     */
    public static String format(Property property) {
        final StringBuilder builder = new StringBuilder();
        builder.append("PostalCode: ")
                .append(property.getPostalCode())
                .append("; Unit: ")
                .append(property.getUnit())
                .append("; Type: ")
                .append(property.getType())
                .append("; Ask: ")
                .append(property.getAsk())
                .append("; Bid: ")
                .append(property.getBid());
        return builder.toString();
    }

    /**
     * Formats the {@code meeting} for display to the user.
     */
    public static String format(Meeting meeting) {
        final StringBuilder builder = new StringBuilder();
        builder.append("MeetingTitle: ")
                .append(meeting.getMeetingTitle())
                .append("; MeetingDate: ")
                .append(meeting.getMeetingDate())
                .append("; Buyer's Phone Number: ")
                .append(meeting.getBuyerPhone())
                .append("; Seller's Phone Number: ")
                .append(meeting.getSellerPhone())
                .append("; Type: ")
                .append(meeting.getType())
                .append("; PostalCode: ")
                .append(meeting.getPostalCode());
        return builder.toString();
    }
}
