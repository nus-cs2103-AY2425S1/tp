package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "delete-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified by the tag name.\n"
            + "Parameters: TAG_NAME (must exists in the AddressBook)\n"
            + "Example: " + COMMAND_WORD + " t/florist";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_DELETE_TAG_FAILURE_STILL_TAGGED = "The Tag: %1$s is still used";
    public static final String MESSAGE_DELETE_TAG_FAILURE_NOT_FOUND = "The Tag: %1$s does not exist";

    private final Tag targetTag;

    public DeleteTagCommand(Tag targetTag) {
        this.targetTag = targetTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tag> allTags = model.getFilteredTagList();

        for (Tag tag : allTags) {
            if (tag.getTagName().equals(targetTag.getTagName())) {
                if (tag.canBeDeleted()) {
                    model.deleteTag(tag);
                    return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, Messages.format(targetTag)));
                } else {
                    throw new CommandException(
                            String.format(MESSAGE_DELETE_TAG_FAILURE_STILL_TAGGED, Messages.format(targetTag)));
                }
            }
        }
        throw new CommandException(String.format(MESSAGE_DELETE_TAG_FAILURE_NOT_FOUND, Messages.format(targetTag)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherDeleteTagCommand = (DeleteTagCommand) other;
        return targetTag.equals(otherDeleteTagCommand.targetTag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetTag", targetTag)
                .toString();
    }
}
