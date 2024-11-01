package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Command that can be undone
 */
public interface UndoableCommand {
    /**
     * Reverts to the previous state of the address book
     */
    void undo(Model model);
}
