package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Recovers the previous version of CampusConnect.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Campus Connect has recovered!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.undoCampusConnect();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UndoCommand)) {
            return false;
        }

        return true;
    }
}
