package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_FAILURE = "No commands to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}