package tuteez.logic.commands;

import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private static final Logger logger = LogsCenter.getLogger(HelpCommand.class);

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing help command. Displaying help window.");
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
