package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Filters the contact list based on name and/or tags.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the contact list by name and/or tags.\n"
            + "Parameters: [n/NAME] [t/TAG]\n"
            + "Example: " + COMMAND_WORD + " n/John t/client";

    public static final String MESSAGE_NO_CONTACT_FOUND = "No contacts match the filter criteria.";

    private final String name;
    private final String tagName;

    /**
     * Class that handles FilterCommand
     */
    public FilterCommand(String name, String tagName) {
        this.name = name;
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> predicate = person -> {
            boolean nameMatches = name == null || name.isEmpty()
                    || person.getName().toString().toLowerCase().contains(name.toLowerCase());
            boolean tagMatches = tagName == null || tagName.isEmpty()
                    || person.getTags().stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase(tagName));
            return nameMatches
                    && tagMatches;
        };

        model.updateFilteredPersonList(predicate);

        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACT_FOUND);
        }

        return new CommandResult(constructSuccessMessage(this.name, this.tagName));
    }

    /**
     * Dynamically produced the success message based on the name and tag
     */
    public static String constructSuccessMessage(String name, String tagName) {
        StringBuilder messageBuilder = new StringBuilder("Displaying filtered results: ");
        if (name != null && !name.isEmpty()) {
            messageBuilder.append("Name: ").append(name).append(" ");
        }
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

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherCommand = (FilterCommand) other;

        boolean isNameEqual = (name == null && otherCommand.name == null)
                || (name != null && name.equals(otherCommand.name));

        boolean isTagNameEqual = (tagName == null && otherCommand.tagName == null)
                || (tagName != null && tagName.equals(otherCommand.tagName));

        return isNameEqual && isTagNameEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("tagName", tagName)
                .toString();
    }
}
