package seedu.address.logic.commands;


import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the application status of an existing company in the address book
 */
public class ApplicationStatusCommand extends Command {
    public static final String COMMAND_WORD = "status";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "This command has yet to be implemented";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
