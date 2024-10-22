package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Filters the list of contacts displayed based on a given tag
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters contacts for a given tag.\n"
            + "The given tag must be an exact match with the intended tag to find.\n"
            + "If there is no contact with the given tag, "
            + "an empty list of contacts will be displayed.\n"
            + "Ensure that tag only contains alphanumeric characters. \n"
            + "Parameters: " + PREFIX_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " t/supplier";

    public static final String MESSAGE_SUCCESS = "Filtered for tag(s): %s";
    public static final String MESSAGE_FAILURE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE)
            + "\nListing all contacts instead.";

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Creates a FilterCommand, filtering for the given {@code tag}
     * @param tags Set of tags to be filtered for in the list of contacts.
     */
    public FilterCommand(Set<Tag> tags) {
        requireAllNonNull(tags);
        this.tags.addAll(tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredPersonList(person ->
                tags.size() <= person.getTags().size()
                        && person.getTags().containsAll(tags)
        );
        String filteredTags = tags.stream()
                .map(Tag::getTagName)
                .collect(Collectors.joining(", "));

        return new CommandResult(String.format(MESSAGE_SUCCESS, filteredTags));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand e = (FilterCommand) other;
        return this.tags.equals(e.tags);
    }
}
