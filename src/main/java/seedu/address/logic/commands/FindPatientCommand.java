package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Finds and lists all patients in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindPatientCommand extends Command {
    public static final String COMMAND_WORD = "find-patient";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("find-patient command not implemented yet");
    }
}
