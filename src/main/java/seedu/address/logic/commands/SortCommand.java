package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the contact list by tag values in ascending or descending order.\n"
            + "Parameters: [t/TAG asc/desc]\n"
            + "Example : " + COMMAND_WORD + " t/friends asc\n";

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

        Predicate<Person> predicateMatch = person -> {
            boolean tagMatches = tagName != null && !tagName.isEmpty()
                    && person.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase(tagName));
            return tagMatches;
        };

        Comparator<Person> sortOrderComparator = new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                Optional<Tag> p1Tag = p1.getTags().stream().filter(tag -> tag.tagName.equalsIgnoreCase(tagName))
                        .findFirst();
                Optional<Tag> p2Tag = p2.getTags().stream().filter(tag -> tag.tagName.equalsIgnoreCase(tagName))
                        .findFirst();

                int compareResult = 0;

                if (p1Tag.isEmpty() || p2Tag.isEmpty()) {
                    return compareResult;
                }

                if (p1Tag.get().tagValue == null && p2Tag.get().tagValue == null) {
                    return compareResult;
                } else if (p1Tag.get().tagValue == null) {
                    return 1;
                } else if (p2Tag.get().tagValue == null) {
                    return -1;
                }

                Double p1TagDouble = tryParseDouble(p1Tag.get().tagValue);
                Double p2TagDouble = tryParseDouble(p2Tag.get().tagValue);

                if (p1TagDouble != null && p2TagDouble != null) {
                    if (p1TagDouble > p2TagDouble) {
                        compareResult = 1;
                    } else if (p1TagDouble < p2TagDouble) {
                        compareResult = -1;
                    }
                } else if (p1TagDouble == null && p2TagDouble == null) {
                    if (p1Tag.get().tagValue.compareTo(p2Tag.get().tagValue) > 0) {
                        compareResult = 1;
                    } else if (p1Tag.get().tagValue.compareTo(p2Tag.get().tagValue) < 0) {
                        compareResult = -1;
                    }
                } else if (p1TagDouble != null) {
                    compareResult = -1;
                } else if (p2TagDouble != null) {
                    compareResult = 1;
                }

                if (sortOrder.equalsIgnoreCase("ASC")) {
                    return compareResult;
                } else {
                    return -compareResult;
                }
            }
        };

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

        boolean isTagNameEqual = (tagName == null && otherCommand.tagName == null)
                || (tagName != null && tagName.equals(otherCommand.tagName));
        return isTagNameEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tagName", tagName)
                .toString();
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
