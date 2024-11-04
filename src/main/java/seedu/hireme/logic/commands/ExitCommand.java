package seedu.hireme.logic.commands;

import seedu.hireme.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "/exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting HireMe as requested ...";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Closes the application.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false,
                true, false, null);
    }
}
