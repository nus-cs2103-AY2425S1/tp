package seedu.ddd.logic.commands;

import seedu.ddd.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String COMMAND_DESCRIPTION = COMMAND_WORD + ": shows program usage instructions";
    public static final String COMMAND_USAGE = "usage: " + COMMAND_WORD;
    public static final String MESSAGE_USAGE = COMMAND_DESCRIPTION + "\n" + COMMAND_USAGE;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
