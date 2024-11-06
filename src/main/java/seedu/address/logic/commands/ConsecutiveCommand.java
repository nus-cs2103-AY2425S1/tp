package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Handles detection and response for consecutive command executions.
 */
public class ConsecutiveCommand extends Command {

    public static final String MESSAGE_CONSECUTIVE_COMMAND = "Command: '%s' has already been executed.";

    private final String commandWord;
    private final String argument;

    /**
     * Creates a ConsecutiveCommand to notify consecutive execution.
     *
     * @param commandWord The command word of the current command.
     */

    public ConsecutiveCommand(String commandWord, String arguments) {
        this.commandWord = commandWord;
        this.argument = arguments;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(MESSAGE_CONSECUTIVE_COMMAND, commandWord, argument));
    }
}
