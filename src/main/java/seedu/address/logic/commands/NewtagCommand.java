package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

<<<<<<< HEAD
import javax.swing.text.html.HTML;
=======
import java.util.List;
>>>>>>> feature-mass-newtag

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagList;

/**
 * Adds a new predefined tag.
 */
public class NewtagCommand extends Command {
    public static final String COMMAND_WORD = "newtag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new tag (case insensitive).\n"
            + "Parameters: TAG_NAME (MAX 50 alphanumeric characters, spaces, parenthesis and apostrophes)\n"
            + "Example: " + COMMAND_WORD + " t/Bride's Friend";

    public static final String MESSAGE_SUCCESS = "New tag added: ";
    public static final String MESSAGE_DUPLICATE = "This tag already exists.\n";
    public static final String MESSAGE_TOO_MANY_TAGS = "You have more than " + TagList.MAXIMUM_TAGLIST_SIZE
            + " tags.\nPlease remove some using deletetag.\n";

    private final List<Tag> tags;


    /**
     * @param tags The tags to be added.
     */
    public NewtagCommand(List<Tag> tags) {
        requireAllNonNull(tags);
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
<<<<<<< HEAD
        if (!model.checkAcceptableTagListSize()) {
            throw new CommandException(MESSAGE_TOO_MANY_TAGS);
        }
        boolean isSuccessful = model.addTag(tag);
=======
        boolean isSuccessful = model.addTags(tags);
>>>>>>> feature-mass-newtag
        if (!isSuccessful) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }
        String successMessage = MESSAGE_SUCCESS + " " + tags + "\n";

        model.updateTagList();

        String currentTags = "Your tags: " + model.getTagList();
        return new CommandResult(successMessage + currentTags);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof NewtagCommand)) {
            return false;
        }

        NewtagCommand otherCommand = (NewtagCommand) other;
        return tags.equals(otherCommand.tags);
    }
}
