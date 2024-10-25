package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Represents whether a command can be undone.
 */
public interface Undoable {
    /**
     * Undoes the effects of this command.
     */
    boolean undo(Model model);
}
