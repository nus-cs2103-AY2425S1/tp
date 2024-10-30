package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class CancelCommand extends Command {
    public static final String COMMAND_WORD = "cancel";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels the operation the user tries to do.\n"
            + "After the user has entered a important command (delete/clear), the user can cancel the command by "
            + "typing 'cancel'.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.clearSavedCommand();

        return new CommandResult("Operation cancelled.");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof CancelCommand;
    }
}
