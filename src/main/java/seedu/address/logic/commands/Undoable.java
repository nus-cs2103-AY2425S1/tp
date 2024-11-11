package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents a command which can be undone by the undo command.
 */
public interface Undoable {
    /**
     * Undoes the effects of the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    CommandResult undo(Model model);
}
