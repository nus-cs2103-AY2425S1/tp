package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Prompts the user to confirm the operation.
 */
public class ConfirmPrompt extends Command {
    public static final String MESSAGE_CONFIRM_PROMPT = "Please type in command 'confirm' to confirm the operation.\n"
            + "Otherwise, type in command 'cancel', or any other input to cancel the operation.";

    private final Command savedCommand;

    public ConfirmPrompt(Command savedCommand) {
        this.savedCommand = savedCommand;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        savedCommand.validateInput(model);
        model.setSavedCommand(savedCommand);

        String resultString = MESSAGE_CONFIRM_PROMPT + "\nOperation: " + savedCommand;

        return new CommandResult(resultString, false, false, true, false,
                -1, false, null, null, null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ConfirmPrompt otherConfirmPrompt)) {
            return false;
        }
        return savedCommand.equals(otherConfirmPrompt.savedCommand);
    }

    @Override
    public String toString() {
        return "Confirmation prompt of: " + savedCommand.toString();
    }
}
