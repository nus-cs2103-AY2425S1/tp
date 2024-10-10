package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Adds tags to a person.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to a person. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TAG + "TAG ";

    public static final String MESSAGE_SUCCESS = "New tag added";
    private final Index targetIndex;
    private final Set<Tag> tagList;

    /**
     * Creates a TagCommand to add the specified {@code Set<Tag>}
     * to the person of specified {@code Index}
     */
    public TagCommand(Index targetIndex, Set<Tag> tagList) {
        this.targetIndex = targetIndex;
        this.tagList = tagList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());
        model.setTag(person, tagList);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(person)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return tagList.equals(otherTagCommand.tagList)
                && targetIndex.equals(otherTagCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tagList)
                .toString();
    }
}
