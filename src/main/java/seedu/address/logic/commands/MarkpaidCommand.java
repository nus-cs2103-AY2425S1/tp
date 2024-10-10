package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class MarkpaidCommand extends Command {
    public static final String COMMAND_WORD = "markpaid";
    /*public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PAID + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PAID + "Likes to swim.";*/

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
