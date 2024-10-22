package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = ":help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened command cheatsheet.";
    public static final String COMMAND_SUMMARY_ACTION = "Help";
    public static final String COMMAND_SUMMARY_FORMAT =
            ":help";
    public static final String COMMAND_SUMMARY_EXAMPLES =
            ":help";



    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
