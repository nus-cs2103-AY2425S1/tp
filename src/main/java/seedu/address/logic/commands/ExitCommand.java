package seedu.address.logic.commands;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ExitCommandResult;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting ClinicConnect System as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new ExitCommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true);
    }

}
