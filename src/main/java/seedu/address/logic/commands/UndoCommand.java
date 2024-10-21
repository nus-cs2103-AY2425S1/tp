package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Undoes the previous concrete command.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successful";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
