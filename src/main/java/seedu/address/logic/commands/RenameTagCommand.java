package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Renames a tag in the tag list.
 */
public class RenameTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "renametag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames an existing tag.\n"
            + "Example: " + COMMAND_WORD + " t/bride's side t/groom's side";

    public static final String MESSAGE_SUCCESS = "Tag has been renamed. ";
    public static final String MESSAGE_DUPLICATE = "This tag already exists:\n";

    public static final String MESSAGE_NONEXISTENT = "This tag does not exist:\n";

    private final Tag existingTag;
    private final String newTagName;

    /**
     * Constructs a RenameTagCommand to rename the specified {@code tag}.
     *
     * @param existingTag The tag to be renamed.
     * @param newTagName The new name of the tag, after renaming.
     */
    public RenameTagCommand(Tag existingTag, String newTagName) {
        requireAllNonNull(existingTag);
        this.existingTag = existingTag;
        this.newTagName = newTagName;
    }

    /**
     * Checks if the criteria for renaming of tag have been met.
     *
     * @param model the model handling the renaming.
     * @throws CommandException if the renaming should not be allowed to occur.
     */
    private void validateInputs(Model model) throws CommandException {
        String messageNonExistent = MESSAGE_NONEXISTENT + existingTag;
        String messageDuplicate = MESSAGE_DUPLICATE + new Tag(newTagName);
        String newLine = "\n";

        // Failure due to non-existent original tag and duplicate
        if (!model.hasTag(existingTag) && model.hasTag(new Tag(newTagName))) {
            throw new CommandException(messageNonExistent + newLine + messageDuplicate);
        }

        // Failure due to non-existent original tag only
        if (!model.hasTag(existingTag)) {
            throw new CommandException(messageNonExistent);
        }

        // Failure due to duplicate only
        if (model.hasTag(new Tag(newTagName))) {
            throw new CommandException(messageDuplicate);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        validateInputs(model);

        model.renameTag(existingTag, newTagName);
        model.editTagInPersons(existingTag, newTagName);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateTagList();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void undo(Model model) {
        requireAllNonNull(model);
        model.renameTag(new Tag(newTagName), existingTag.getTagName());
        model.editTagInPersons(new Tag(newTagName), existingTag.getTagName());
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
