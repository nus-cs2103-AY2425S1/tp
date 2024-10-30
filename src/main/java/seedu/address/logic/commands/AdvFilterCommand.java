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
    private final Operator operator;

    /**
     * Class that handles Operator enum type used in SortCommand
     */
    public static enum Operator {
        GREATER_THAN(">"),
        GREATER_THAN_OR_EQUAL(">="),
        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL("<="),
        EQUAL("="),
        NOT_EQUAL("!=");

        private final String keyword;

        Operator(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return this.keyword;
        }

    }

    /**
     * Class that handles SortCommand
     */
    public AdvFilterCommand(String tagName, Operator operator, String tagValue) {
        this.tagName = tagName;
        this.operator = operator;
        this.tagValue = tagValue;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> predicate = person -> {
            boolean tagMatches = person.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase(tagName)
                    && tag.tagValue != null ? compare(operator, tag, tagValue) : false);
            return tagMatches;
        };

        model.updateFilteredPersonList(predicate);

        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACT_FOUND);
        }

        return new CommandResult(constructSuccessMessage(this.tagName, this.operator.getKeyword(), this.tagValue));
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
                .add("operator", operator.getKeyword())
                .add("tagValue", tagValue)
                .toString();
    }

    /**
     * Compares Person's existing tag value with given filter tag value
     * using Operator.
     * Returns true if existing tag value matches filter criteria, false otherwise.
     */
    public boolean compare(Operator operator, Tag tag, String tagValue) {
        return switch (operator) {
        case EQUAL -> tag.tagValue.equalsIgnoreCase(tagValue);
        case NOT_EQUAL -> !tag.tagValue.equalsIgnoreCase(tagValue);
        case GREATER_THAN-> {
            Integer doubleResult = compareDouble(tag.tagValue, tagValue);
            if (doubleResult != null) {
                yield doubleResult > 0;
            }
            Integer stringResult = compareString(tag.tagValue, tagValue);
            if (stringResult != null) {
                yield stringResult > 0;
            }
            yield false;
        }
        case LESS_THAN -> {
            Integer doubleResult = compareDouble(tag.tagValue, tagValue);
            if (doubleResult != null) {
                yield doubleResult < 0;
            }
            Integer stringResult = compareString(tag.tagValue, tagValue);
            if (stringResult != null) {
                yield stringResult < 0;
            }
            yield false;
        }
        case LESS_THAN_OR_EQUAL -> {
            Integer doubleResult = compareDouble(tag.tagValue, tagValue);
            if (doubleResult != null) {
                yield doubleResult <= 0;
            }
            Integer stringResult = compareString(tag.tagValue, tagValue);
            if (stringResult != null) {
                yield stringResult <= 0;
            }
            yield false;
        }
        case GREATER_THAN_OR_EQUAL -> {
            Integer doubleResult = compareDouble(tag.tagValue, tagValue);
            if (doubleResult != null) {
                yield doubleResult >= 0;
            }
            Integer stringResult = compareString(tag.tagValue, tagValue);
            if (stringResult != null) {
                yield stringResult >= 0;
            }
            yield false;
        }
        default -> throw new IllegalArgumentException("Unknown operator");

        };
    }

    private Integer compareDouble(String currentValue, String testValue) {
        Double currentValueDouble = tryParseDouble(currentValue);
        Double testValueDouble = tryParseDouble(testValue);
        if (currentValueDouble == null || testValueDouble == null) {
            return null;
        }
        return Double.compare(currentValueDouble, testValueDouble);
    }

    private Integer compareString(String currentValue, String testValue) {
        if (tryParseDouble(currentValue) != null || tryParseDouble(testValue) != null) {
            return null;
        }
        return currentValue.compareTo(testValue);
    }

    /**
     * Checks if user's command input contains a valid operator.
     * Returns matching Operator object if valid, null otherwise.
     */
    public static Operator matchOperator(String string) {
        Operator operatorType = null;
        for (Operator operator : Operator.values()) {
            if (string.equalsIgnoreCase(operator.getKeyword())) {
                operatorType = operator;
                break;
            }
        }
        return operatorType;
    }

    /**
     * Tries to parse given String into a Double.
     * Returns a valid Double object if parsable, null otherwise.
     */
    public Double tryParseDouble(String value) {
        try {
            double d = Double.parseDouble(value);
            return d;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
