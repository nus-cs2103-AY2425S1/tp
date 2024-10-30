package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Sort the contact list based on tag names and values.
 */
public class AdvFilterCommand extends Command {

    public static final String COMMAND_WORD = "advfilter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the contact list by tag values.\n"
            + "Parameters: t/TAG OPERATOR VALUE\n"
            + "Available Operators: [=, !=, <, <=, >, >=]\n"
            + "Example 1: " + COMMAND_WORD + " t/friends >= 1\n"
            + "Example 2: " + COMMAND_WORD + " t/priority != low\n";

    public static final String MESSAGE_NO_CONTACT_FOUND = "No contacts match the filter criteria.";

    private final String tagName;
    private final String tagValue;
    private final String operator;

    /**
     * Class that handles SortCommand
     */
    public AdvFilterCommand(String tagName, String operator, String tagValue) {
        this.tagName = tagName;
        this.operator = operator;
        this.tagValue = tagValue;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> predicate = person -> {
            boolean tagValueMatches = tagValue == null || tagValue.isEmpty()
                    || person.getTags().stream()
                            .anyMatch(tag -> tag.tagValue != null ? compare(operator, tag, tagValue) : false);
            boolean tagMatches = tagName == null || tagName.isEmpty()
                    || person.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase(tagName));
            return tagValueMatches
                    && tagMatches;
        };

        model.updateFilteredPersonList(predicate);

        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACT_FOUND);
        }

        return new CommandResult(constructSuccessMessage(this.tagName, this.operator, this.tagValue));
    }

    /**
     * Dynamically produced the success message based on the name and tag
     */
    public static String constructSuccessMessage(String tagName, String operator, String tagValue) {
        StringBuilder messageBuilder = new StringBuilder("Displaying filtered results: ");
        if (tagName != null && !tagName.isEmpty()) {
            messageBuilder.append("Tag: ").append(tagName).append(" ");
        }
        if (operator != null && !operator.isEmpty()) {
            messageBuilder.append("Operator: ").append(operator).append(" ");
        }
        if (tagValue != null && !tagValue.isEmpty()) {
            messageBuilder.append("Tag Value: ").append(tagValue).append(" ");
        }
        return messageBuilder.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AdvFilterCommand)) {
            return false;
        }

        AdvFilterCommand otherCommand = (AdvFilterCommand) other;

        boolean isTagNameEqual = (tagName == null && otherCommand.tagName == null)
                || (tagName != null && tagName.equals(otherCommand.tagName));

        boolean isOperatorEqual = (operator == null && otherCommand.operator == null)
                || (operator != null && operator.equals(otherCommand.operator));


        boolean isTagValueEqual = (tagValue == null && otherCommand.tagValue == null)
                || (tagValue != null && tagValue.equals(otherCommand.tagValue));
        return isTagNameEqual && isOperatorEqual && isTagValueEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tagName", tagName)
                .add("operator", operator)
                .add("tagValue", tagValue)
                .toString();
    }

    boolean compare(String operator, Tag tag, String tagValue) {
        return switch (operator) {
        case "=" -> tag.tagValue.equalsIgnoreCase(tagValue);
        case "!=" -> !tag.tagValue.equalsIgnoreCase(tagValue);
        case ">" -> {
            Double currentTagValue = tryParseDouble(tag.tagValue);
            Double testTagValue = tryParseDouble(tagValue);
            yield (currentTagValue != null && testTagValue != null)
                    ? Double.compare(currentTagValue, testTagValue) > 0
                    : false;
        }
        case "<" -> {
            Double currentTagValue = tryParseDouble(tag.tagValue);
            Double testTagValue = tryParseDouble(tagValue);
            yield (currentTagValue != null && testTagValue != null)
                    ? Double.compare(currentTagValue, testTagValue) < 0
                    : false;
        }
        case "<=" -> {
            Double currentTagValue = tryParseDouble(tag.tagValue);
            Double testTagValue = tryParseDouble(tagValue);
            yield (currentTagValue != null && testTagValue != null)
                    ? Double.compare(currentTagValue, testTagValue) <= 0
                    : false;
        }
        case ">=" -> {
            Double currentTagValue = tryParseDouble(tag.tagValue);
            Double testTagValue = tryParseDouble(tagValue);
            yield (currentTagValue != null && testTagValue != null)
                    ? Double.compare(currentTagValue, testTagValue) >= 0
                    : false;
        }
        default -> throw new IllegalArgumentException("Unknown operator");

        };
    }

    Double tryParseDouble(String value) {
        try {
            double d = Double.parseDouble(value);
            return d;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
