package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Adds a new predefined tag.
 */
public class NewtagCommand extends Command {
    public static final String COMMAND_WORD = "newtag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new tag.\n"
            + "Parameters: TAG_NAME (MAX 50 alphanumeric characters)\n"
            + "Example: " + COMMAND_WORD + " Bride's Friend";

    private final Tag tag;

    /**
     * @param tag The tag object to be added.
     */
    public NewtagCommand(Tag tag) {
        requireAllNonNull(tag);
        this.tag = tag;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Valid newtag Command Received: " + tag);
    }
}
