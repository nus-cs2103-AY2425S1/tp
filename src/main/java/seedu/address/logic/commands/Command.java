package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final String[] ACTION_COMMANDS = {
        AddCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        AddAppointmentCommand.COMMAND_WORD, DeleteAppointmentCommand.COMMAND_WORD,
        AddSchemeCommand.COMMAND_WORD, DeleteSchemeCommand.COMMAND_WORD,
    };

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract String getCommandWord();

    public abstract String undo(Model model, CommandHistory pastCommands);

}
