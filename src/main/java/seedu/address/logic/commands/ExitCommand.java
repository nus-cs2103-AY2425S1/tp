package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting ResearchRoster. See you again soon!";

    /**
     * Executes the exit command, signaling the application to terminate.
     *
     * @param model the {@code Model} that the command should operate on.
     * @return a {@code CommandResult} containing a message acknowledging the exit action,
     *         with the {@code isExit} flag set to {@code true}.
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
