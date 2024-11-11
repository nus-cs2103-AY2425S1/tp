package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Sort the contact list based on tag names and values.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String ASCENDING_KEYWORD = "ASC";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the contact list by tag values in ascending or descending order.\n"
            + "Parameters: "
            + PREFIX_TAG + "TAG "
            + "asc/desc\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friends asc";


    public static final String MESSAGE_NO_CONTACT_FOUND = "No contacts match the sort criteria.";

    private final String tagName;
    private final String sortOrder;

    /**
     * Class that handles SortCommand
     */
    public SortCommand(String tagName, String sortOrder) {
        this.tagName = tagName;
        this.sortOrder = sortOrder;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Person> predicateMatch = this::predicateMatch;
        Comparator<Person> sortOrderComparator = this::sortOrderComparator;
        model.updateFilteredPersonList(predicateMatch, sortOrderComparator);
        List<Person> filteredList = model.getFilteredPersonList();
        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACT_FOUND);
        }
        return new CommandResult(constructSuccessMessage(this.tagName));
    }

    /**
     * Dynamically produced the success message based on the name and tag
     */
    public static String constructSuccessMessage(String tagName) {
        StringBuilder messageBuilder = new StringBuilder("Displaying sorted results: ");
        if (tagName != null && !tagName.isEmpty()) {
            messageBuilder.append("Tag: ").append(tagName).append(" ");
        }

        return messageBuilder.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SortCommand)) {
            return false;
        }
        SortCommand otherCommand = (SortCommand) other;
        return (tagName == null && otherCommand.tagName == null)
                || (tagName != null && tagName.equals(otherCommand.tagName));
    }

    private boolean predicateMatch(Person person) {
        return tagName != null
                && !tagName.isEmpty()
                && person.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase(tagName));
    }

    private int sortOrderComparator(Person p1, Person p2) {
        Optional<Tag> p1Tag = getTagByName(p1);
        Optional<Tag> p2Tag = getTagByName(p2);

        if (areTagsEmpty(p1Tag, p2Tag)) {
            return 0;
        }
        if (isEitherTagNull(p1Tag, p2Tag)) {
            return compareNullTagValues(p1Tag, p2Tag);
        }

        Double p1TagDouble = tryParseDouble(p1Tag.get().tagValue);
        Double p2TagDouble = tryParseDouble(p2Tag.get().tagValue);

        int compareResult = getCompareResult(p1TagDouble, p2TagDouble, p1Tag, p2Tag);

        return determineComparisonOrder(compareResult, sortOrder.equalsIgnoreCase(ASCENDING_KEYWORD));
    }

    private static int getCompareResult(Double p1TagDouble,
                                        Double p2TagDouble,
                                        Optional<Tag> p1Tag,
                                        Optional<Tag> p2Tag) {
        if (p1TagDouble != null && p2TagDouble != null) {
            return Double.compare(p1TagDouble, p2TagDouble);
        }
        if (p1TagDouble == null && p2TagDouble == null) {
            return p1Tag.get().tagValue.compareToIgnoreCase(p2Tag.get().tagValue);
        }
        return (p1TagDouble != null)
                ? -1
                : 1;
    }


    private boolean areTagsEmpty(Optional<Tag> p1Tag, Optional<Tag> p2Tag) {
        return (p1Tag.isEmpty() || p2Tag.isEmpty());
    }

    private boolean isEitherTagNull(Optional<Tag> p1Tag, Optional<Tag> p2Tag) {
        return (p1Tag.get().tagValue == null || p2Tag.get().tagValue == null);
    }

    private boolean areBothTagsNull(Optional<Tag> p1Tag, Optional<Tag> p2Tag) {
        return (p1Tag.get().tagValue == null && p2Tag.get().tagValue == null);
    }

    private int compareNullTagValues(Optional<Tag> p1Tag, Optional<Tag> p2Tag) {
        if (areBothTagsNull(p1Tag, p2Tag)) {
            return 0;
        } else if (p1Tag.get().tagValue == null) {
            return 1;
        } else {
            return -1;
        }
    }

    private Optional<Tag> getTagByName(Person person) {
        return person.getTags().stream()
                .filter(tag -> tag.tagName.equalsIgnoreCase(tagName))
                .findFirst();
    }

    private int determineComparisonOrder(int compareResult, boolean isAscending) {
        return isAscending
                ? compareResult
                : -compareResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tagName", tagName)
                .toString();
    }

    Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
