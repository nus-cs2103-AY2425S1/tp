package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the remark of an existing person in the address book.
 */
public class EmergencyContactCommand extends Command {

    public static final String COMMAND_WORD = "emergency";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the emergency contact details of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "[EMERGENCY CONTACT NAME]"
            + PREFIX_PHONE + "[EMERGENCY CONTACT NUMBER\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Richard Ng "
            + PREFIX_PHONE + "82943718";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Emergency contact command not implemented yet";

    @Override
    public CommandResult execute (Model model) throws CommandException {
            throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
