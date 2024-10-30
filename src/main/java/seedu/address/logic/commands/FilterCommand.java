package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the contact list by name and/or tags.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + PREFIX_TAG + "client "
            + PREFIX_TAG + "friend";


    public static final String MESSAGE_NO_CONTACT_FOUND = "No contacts match the filter criteria.";

    private final Set<String> names;
    private final Set<Tag> tags;

    /**
     * Constructs a FilterCommand.
     *
     * @param names The set of name to filter by.
     * @param tags The set of tags to filter by.
     */
    public FilterCommand(Set<String> names, Set<Tag> tags) {
        this.names = names;
        this.tags = tags;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> predicate = person -> {
            // Check if the person's name matches any of the filter names (if provided)
            boolean nameMatches = names.isEmpty() || names.stream()
                    .anyMatch(name -> person.getName().toString().toLowerCase().contains(name.toLowerCase()));

            // Check if the person's tags contain all filter tags (if provided)
            boolean tagsMatch = tags.isEmpty() || person.getTags().containsAll(tags);

            return nameMatches && tagsMatch;
        };

        // Update the filtered person list based on the predicate
        model.updateFilteredPersonList(predicate);

        List<Person> filteredList = model.getFilteredPersonList();

        if (filteredList.isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACT_FOUND);
        }

        return new CommandResult(constructSuccessMessage(names, tags));
    }

    /**
     * Constructs the success message based on the name and tags.
     */
    public static String constructSuccessMessage(Set<String> names, Set<Tag> tags) {
        StringBuilder messageBuilder = new StringBuilder("Displaying filtered results: ");

        if (!names.isEmpty()) {
            String namesString = String.join(", ", names);
            messageBuilder.append("Names: ").append(namesString).append(" ");
        }

        if (!tags.isEmpty()) {
            String tagsString = tags.stream()
                    .map(tag -> tag.tagName)
                    .collect(Collectors.joining(", "));
            messageBuilder.append("Tags: ").append(tagsString);
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

        boolean isNameEqual = (names == null && otherCommand.names == null)
                || (names != null && names.equals(otherCommand.names));

        boolean isTagsEqual = (tags == null && otherCommand.tags == null)
                || (tags != null && tags.equals(otherCommand.tags));

        return isNameEqual && isTagsEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("names", names)
                .add("tags", tags)
                .toString();
    }
}
