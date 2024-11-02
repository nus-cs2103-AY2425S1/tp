package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Renames a predefined tag.
 */
public class RenameTagCommand extends Command implements UndoableCommand {
    public static final String COMMAND_WORD = "renametag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames an existing tag.\n"
            + "Example: " + COMMAND_WORD + " t/bride's side t/groom's side";

    public static final String MESSAGE_SUCCESS = "Tag has been renamed.";
    public static final String MESSAGE_NONEXISTENT_OR_DUPLICATE = "The tag you wish to rename does not exist, "
            + "or the tag you wish to rename it to already exists.\n";

    private final Tag existingTag;
    private final String newTagName;
    private String existingTagName;

    /**
     * @param existingTag The tag to be renamed.
     * @param newTagName The new name of the tag, after renaming.
     */
    public RenameTagCommand(Tag existingTag, String newTagName) {
        requireAllNonNull(existingTag);
        this.existingTag = existingTag;
        this.newTagName = newTagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        existingTagName = existingTag.getTagName();

        if (!model.renameTag(existingTag, newTagName)) {
            throw new CommandException(MESSAGE_NONEXISTENT_OR_DUPLICATE);
        }

        model.editTagInPersons(existingTag, newTagName);
        model.updateTagList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Undoes the previous RenameTag command
     */
    public void undo(Model model) {
        requireAllNonNull(model);
        model.renameTag(new Tag(newTagName), existingTagName);
        model.editTagInPersons(new Tag(newTagName), existingTagName);
        model.updateTagList();
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
                .add("existingTag", existingTag)
                .add("newTagName", newTagName)
                .toString();
    }
}
