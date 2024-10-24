package tuteez.logic.commands;

import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    private static final Logger logger = LogsCenter.getLogger(ExitCommand.class);

    @Override
    public CommandResult execute(Model model) {
        logger.info("Exit command received. Exiting Tuteez and shutting down application.");
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
