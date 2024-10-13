package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Filters the contact list based on name and/or tags.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the contact list by name and/or tags.\n"
            + "Parameters: [n/NAME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " n/John t/client t/friend";

    public static final String MESSAGE_NO_CONTACT_FOUND = "No contacts match the filter criteria.";

    private final String name;
    private final Set<Tag> tags;

    /**
     * Constructs a FilterCommand.
     *
     * @param name The name to filter by.
     * @param tags The set of tags to filter by.
     */
    public FilterCommand(String name, Set<Tag> tags) {
        this.name = name;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> predicate = person -> {
            // Check if the person's name matches the filter name (if provided)
            boolean nameMatches = name == null || name.isEmpty()
                    || person.getName().toString().toLowerCase().contains(name.toLowerCase());

            // Check if the person's tags contain all filter tags (if provided)
            boolean tagsMatch = tags.isEmpty()
                    || person.getTags().containsAll(tags);

            return nameMatches && tagsMatch;
        };

        // Update the filtered person list based on the predicate
        model.updateFilteredPersonList(predicate);

        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACT_FOUND);
        }

        return new CommandResult(constructSuccessMessage(name, tags));
    }

    /**
     * Constructs the success message based on the name and tags.
     */
    public static String constructSuccessMessage(String name, Set<Tag> tags) {
        StringBuilder messageBuilder = new StringBuilder("Displaying filtered results: ");
        if (name != null && !name.isEmpty()) {
            messageBuilder.append("Name: ").append(name).append(" ");
        }
        if (!tags.isEmpty()) {
            String tagsString = tags.stream()
                    .map(tag -> tag.tagName)
                    .collect(Collectors.joining(", "));
            messageBuilder.append("Tags: ").append(tagsString).append(" ");
        }
        return messageBuilder.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherCommand = (FilterCommand) other;

        boolean isNameEqual = (name == null && otherCommand.name == null)
                || (name != null && name.equals(otherCommand.name));

        boolean isTagsEqual = (tags == null && otherCommand.tags == null)
                || (tags != null && tags.equals(otherCommand.tags));

        return isNameEqual && isTagsEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("tags", tags)
                .toString();
    }
}
