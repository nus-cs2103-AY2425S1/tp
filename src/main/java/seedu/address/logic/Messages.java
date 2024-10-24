package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
                "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_PERSON_INPUT = "Please enter an existing client name!";
    public static final String MISSING_CLIENT_NAME = "Please enter a client name!";
    public static final String MESSAGE_SUGGESTION = "Did you mean \"%s\"?";

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
                .append("; Appointment: ")
                .append(person.getAppointment())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code listing} for display to the user.
     */
    public static String format(Listing listing) {
        final StringBuilder builder = new StringBuilder();
        builder.append(listing.getName())
                .append("; Price: ")
                .append(listing.getPrice())
                .append("; Area: ")
                .append(listing.getArea())
                .append("; Region: ")
                .append(listing.getRegion())
                .append("; Address: ")
                .append(listing.getAddress())
                .append("; Seller: ")
                .append(listing.getSeller());
        listing.getBuyers().forEach(builder::append);
        return builder.toString();
    }

}
