package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index %1$s provided is invalid!";
    public static final String MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX =
            "The delivery index %1$s provided is invalid!";
    public static final String MESSAGE_INVALID_DUPLICATED_INDEX = "Duplicated index %1$s is not allowed!";
    public static final String MESSAGE_ARCHIVED_PERSON_DISPLAYED_INDEX =
            "The person index provided refers to an archived person.\n"
            + "Unarchive the person to continue";
    public static final String MESSAGE_ARCHIVED_DELIVERY_DISPLAYED_INDEX =
            "The delivery index provided refers to an archived delivery.\n"
            + "Unarchive the delivery to continue.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DELIVERIES_LISTED_OVERVIEW = "%1$d deliveries listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_WINDOW = "This command could not be executed in the inspection window.\n"
            + "Navigate back to the main window to continue.";
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
                .append(person.getAddress());
        if (!person.getTags().isEmpty()) {
            builder.append("; Tags: ");
            person.getTags().forEach(builder::append);
        }
        return builder.toString();
    }

    /**
     * Formats the {@code delivery} for display to the user.
     */
    public static String format(Delivery delivery) {
        final StringBuilder builder = new StringBuilder();
        builder.append(delivery.getItems())
            .append("; ")
            .append(delivery.getAddress())
            .append("; ")
            .append(delivery.getCost())
            .append("; ")
            .append(delivery.getEta())
            .append("; ")
            .append(delivery.getDate())
            .append("; ")
            .append(delivery.getTime());
        return builder.toString();
    }

    /**
     * Formats the list of {@code index} for display to the user.
     */
    public static String formatIndexList(List<Index> indexList) {
        //indexList should not be empty
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < indexList.size(); i++) {
            if (i == 0) {
                builder.append(indexList.get(i).getOneBased());
                continue;
            }
            builder.append(", ").append(indexList.get(i).getOneBased());
        }

        return builder.toString().trim();
    }

    /**
     * Formats the list of {@code person} for display to the user.
     */
    public static String formatPersonList(List<Person> personList) {
        final StringBuilder builder = new StringBuilder();
        for (Person person : personList) {
            builder.append("\n").append(format(person));
        }
        return builder.toString();
    }

    /**
     * Formats the list of {@code delivery} for display to the user.
     */
    public static String formatDeliveryList(List<Delivery> deliveryList) {
        final StringBuilder builder = new StringBuilder();
        for (Delivery delivery : deliveryList) {
            builder.append("\n").append(format(delivery));
        }
        return builder.toString();
    }


}
