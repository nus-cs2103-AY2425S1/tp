package bizbook.logic.commands;

import static java.util.Objects.requireNonNull;

import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;

/**
 * Toggle system between light and dark mode.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles between "
            + "light mode and dark mode.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TOGGLE_SUCCESS = "Toggled theme successfully!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(MESSAGE_TOGGLE_SUCCESS, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ToggleCommand)) {
            return false;
        }
        return true;
    }
}
