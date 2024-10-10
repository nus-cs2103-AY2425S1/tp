package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The client index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
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
     * Formats the {@code client} for display to the user.
     */
    public static String format(Client client) {
        final StringBuilder builder = new StringBuilder();
        builder.append(client.getName())
                .append("; Phone: ")
                .append(client.getPhone())
                .append("; Email: ")
                .append(client.getEmail())
                .append("; Tags: ");
        client.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code RentalInformation} for display to the user.
     */
    public static String formatRentalInformation(RentalInformation rentalInformation) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Address: ")
                .append(rentalInformation.getAddress())
                .append("; Rental Start Date: ")
                .append(rentalInformation.getRentalStartDate())
                .append("; Rental End Date: ")
                .append(rentalInformation.getRentalEndDate())
                .append("; Rent Due Date: ")
                .append(rentalInformation.getRentDueDate())
                .append("; Monthly Rent: ")
                .append(rentalInformation.getMonthlyRent())
                .append("; Deposit: ")
                .append(rentalInformation.getDeposit())
                .append("; Customer List: ")
                .append(rentalInformation.getCustomerList());
        // client.getTags().forEach(builder::append);
        return builder.toString();
    }

}
