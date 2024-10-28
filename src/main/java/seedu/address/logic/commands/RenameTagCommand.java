package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class RenameTagCommand extends Command {
    public static final String COMMAND_WORD = "renametag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames an existing tag.\n"
            + "Example: " + COMMAND_WORD + " t/Bride's Friend t/Friend";

    public static final String MESSAGE_SUCCESS = "Tag has been renamed.";
    public static final String MESSAGE_NONEXISTENT = "The tag you wish to rename does not exist.\n";

    private final Tag existingTag;
    private final String newTagName;

    public RenameTagCommand(Tag existingTag, String newTagName) {
        requireAllNonNull(existingTag);
        this.existingTag = existingTag;
        this.newTagName = newTagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        if (!model.renameTag(existingTag, newTagName)) {
            throw new CommandException(MESSAGE_NONEXISTENT);
        }

        model.updateTagList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof RenameTagCommand)) {
            return false;
        }

        RenameTagCommand otherCommand = (RenameTagCommand) other;

        return existingTag.equals(otherCommand.existingTag)
                && newTagName.equals(otherCommand.newTagName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("existingTag: ", existingTag)
                .add("newTagName: ", newTagName)
                .toString();
    }
}
