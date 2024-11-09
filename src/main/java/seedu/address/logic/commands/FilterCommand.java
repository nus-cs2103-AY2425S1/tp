package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
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
            + ": Filters contacts for given tag(s).\n"
            + "The given tag(s) must be an exact match with the intended tag(s) to find.\n"
            + "If there is no contact with the given tag(s), "
            + "an empty list of contacts will be displayed.\n"
            + "Ensure that the tag(s) only contains alphanumeric characters. \n"
            + "Parameters: " + PREFIX_TAG + "TAG...\n"
            + "Examples:\n"
            + COMMAND_WORD + " t/supplier\n"
            + COMMAND_WORD + " t/supplier" + " t/partner";

    public static final String MESSAGE_SUCCESS = "Filtered for tag(s): %s";
    public static final String MESSAGE_NOT_FOUND =
            "No users found with the tag(s): %s";

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
        /*System.out.println(model.getFilteredPersonList().get(1).getTags().stream()
                .map(Tag::getTagName)
                .collect(Collectors.joining(", ")));*/

        model.updateFilteredPersonList(person ->
                tags.size() <= person.getTags().size()
                        && person.hasAllTags(tags)
        );

        String filteredTags = tags.stream()
                .map(Tag::getTagName)
                .collect(Collectors.joining(", "));
        //if no contacts matches the tag
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NOT_FOUND, filteredTags));
        }
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
