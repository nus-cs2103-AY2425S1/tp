package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ConfirmCommand extends Command {
    public static final String COMMAND_WORD = "confirm";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms the operation the user tries to do.\n"
            + "After the user has entered a important command (delete/clear), the user can confirm the command by typing 'confirm'.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasSavedCommand()) {
            CommandResult result = model.executeSavedCommand();
            model.clearSavedCommand();
            return result;
        }
        model.clearSavedCommand();
        throw new CommandException("No command to confirm.");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof ConfirmCommand;
    }
}
