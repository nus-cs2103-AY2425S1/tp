package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
public class UntagCommand extends Command {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tag(s) from a person. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 " + PREFIX_TAG + "needs consult";

    public static final String MESSAGE_SUCCESS = "Tag(s) deleted";
    public static final String MESSAGE_TAG_DOES_NOT_EXIST = "The tag(s) does not exist";
    private final Index targetIndex;
    private final Set<Tag> tagsToDelete;

    /**
     * Creates a TagCommand to add the specified {@code Set<Tag>}
     * to the person of specified {@code Index}
     */
    public UntagCommand(Index targetIndex, Set<Tag> tagsToDelete) {
        this.targetIndex = targetIndex;
        this.tagsToDelete = tagsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());
        Boolean tagExists = model.tagExists(person, tagsToDelete);

        if (!tagExists) {
            throw new CommandException(MESSAGE_TAG_DOES_NOT_EXIST);
        }
        model.deleteTag(person, tagsToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(person)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UntagCommand)) {
            return false;
        }

        UntagCommand otherUntagCommand = (UntagCommand) other;
        return tagsToDelete.equals(otherUntagCommand.tagsToDelete)
                && targetIndex.equals(otherUntagCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tags", tagsToDelete)
                .toString();
    }
}
