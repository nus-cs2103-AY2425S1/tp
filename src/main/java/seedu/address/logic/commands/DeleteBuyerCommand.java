package seedu.address.logic.commands;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * Represents a command to delete a buyer in the buyer management system.
 */
public class DeleteBuyerCommand extends Command {
    /** The command word for this specific action. */
    public static final String COMMAND_WORD = "deletebuyer";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PHONE + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "Likes to swim.";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "DeleteBuyer command not implemented yet";
    /**
     * Executes the delete buyer command.
     *
     * @param model The model which the command should operate on.
     * @return A CommandResult object representing the result of the delete operation.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
