package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ConfirmPrompt extends Command{
//    public static final String COMMAND_WORD = "confirm";
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms the operation the user tries to do.\n"
//            + "After the user has entered a important command (delete/clear), the user can confirm the command by typing 'confirm'.\n"
//            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_CONFIRM_PROMPT = "Please type in command 'confirm' to confirm the operation.\n"
            + "Otherwise, to cancel the operation.";

    private final Command savedCommand;

    public ConfirmPrompt(Command savedCommand) {
        this.savedCommand = savedCommand;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setSaveCommand(savedCommand);

        return new CommandResult(MESSAGE_CONFIRM_PROMPT, false, false, true);
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
}
