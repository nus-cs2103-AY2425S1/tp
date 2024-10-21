package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Adds a new predefined tag.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "deletetag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an existing tag (case insensitive).\n"
            + "Parameters: TAG_NAME (MAX 50 alphanumeric characters, spaces, parenthesis and apostrophes)\n"
            + "Example: " + COMMAND_WORD + " Bride's Friend";
    public static final String MESSAGE_SUCCESS = "Tag deleted: ";
    public static final String MESSAGE_NONEXISTENT = "This tag does not exist yet.\n";
    private final Tag tag;

    /**
     * @param tag The tag object to be added.
     */
    public DeleteTagCommand(Tag tag) {
        requireAllNonNull(tag);
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        boolean isSuccessful = model.deleteTag(tag);
        if (!isSuccessful) {
            throw new CommandException(MESSAGE_NONEXISTENT);
        }
        String successMessage = MESSAGE_SUCCESS + " " + tag + "\n";
        String currentTags = "Your tags: " + model.getTagList();
        return new CommandResult(successMessage + currentTags);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherCommand = (DeleteTagCommand) other;
        return tag.equals(otherCommand.tag);
    }
}
