package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX = "The concert index provided is invalid";
    public static final String MESSAGE_INVALID_CONCERT_CONTACT_DISPLAYED_INDEX =
            "The concertContact index provided is invalid";
    public static final String MESSAGE_INVALID_CONCERT_CONTACT = "The person provided is not in the provided concert";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_CONCERTS_LISTED_OVERVIEW = "%1$d concerts listed!";
    public static final String MESSAGE_CONCERT_CONTACTS_LISTED_OVERVIEW = "%1$d concertContacts listed!";
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
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Roles: ")
                .append(person.getRole())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code Concert} for display to the user.
     */
    public static String format(Concert concert) {
        final StringBuilder builder = new StringBuilder();
        builder.append(concert.getName()).append("; Address: ").append(concert.getAddress()).append(
                "; Date: ").append(concert.getDate());
        return builder.toString();
    }

    /**
     * Formats the {@code ConcertContact} for display to the user.
     */
    public static String format(ConcertContact concertContact) {
        final StringBuilder builder = new StringBuilder();
        builder.append(format(concertContact.getPerson()))
                .append(format(concertContact.getConcert()));
        return builder.toString();
    }

}
