package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Finds and lists all doctors in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindDoctorCommand extends Command {
    public static final String COMMAND_WORD = "find-doctor";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "find-doctor command not implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
