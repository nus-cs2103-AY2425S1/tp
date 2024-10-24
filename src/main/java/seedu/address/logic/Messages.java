package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "Valid person index must be provided.";
    public static final String MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX = "Valid property index must be provided.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_PHONE_NUMBER_KEYWORDS =
            "The keywords for findp command can only be numbers";
    public static final String MESSAGE_NO_PROPERTIES_TO_DELETE = "No properties to delete.";

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
     * Formats the {@code BuyingProperties} and {@code SellingProperties} for display to the user.
     */
    public static String formatProperties(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n").append(person.getName()).append("\n").append("Buying Properties: ").append("\n");

        int[] index = {1};
        person.getListOfBuyingProperties().forEach(property ->
                builder.append(index[0]++).append(". ").append(property).append("\n")
        );

        builder.append("Selling Properties: ").append("\n");

        index[0] = 1;
        person.getListOfSellingProperties().forEach(property ->
                builder.append(index[0]++).append(". ").append(property).append("\n")
        );

        return builder.toString();
    }

    /**
     * Formats the {@code Propertu} for display to the user when a property is bought or sold.
     */
    public static String formatProperty(Property property) {
        String formattedTags = property.getTags().stream()
                .map(Tag::toString) // Convert each Tag object to its String representation
                .collect(Collectors.joining(", ")); // Join with a comma and space
        String actualPrice = (property.getActualPrice() != null) ? property.getActualPrice().value : "null";
        return "Postal Code: " + property.getPostalCode() + "; " + " Unit Number: " + property.getUnitNumber()
                + "; " + " Price: " + actualPrice + "; Tags: " + formattedTags;
    }

}
