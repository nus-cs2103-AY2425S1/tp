package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Save command that saves the address book state to a save file.
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_SUCCESS = "Address book has been saved!";
    private static final boolean IS_UNDOABLE = false;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (IS_UNDOABLE) {
            model.addCommandToLog(this);
        }
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }

    @Override
    public void undo(Model model) {

    }

    @Override
    public boolean canBeUndone() {
        return IS_UNDOABLE;
    }
}
