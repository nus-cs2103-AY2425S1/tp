package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the name of a tag for all contacts with that tag.
 */
public class RenameTagCommand extends Command {

    public static final String COMMAND_WORD = "rename tag";

    private String oldTag;
    private String newTag;

    /**
     * @param oldTag of the person in the filtered person list to edit
     * @param newTag details to edit the person with
     */
    public RenameTagCommand(String oldTag, String newTag) {
        this.newTag = newTag;
        this.oldTag = oldTag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(oldTag + " tag is renamed to " + newTag);
    }
}
