package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents a Command that can be undone
 */
public abstract class UndoableCommand extends Command {

    /**
     * Undoes the execute method of the command
     */
    public abstract void undo(Model model);
}
