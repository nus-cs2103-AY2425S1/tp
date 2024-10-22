package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ModCommand extends Command{

    public static final String COMMAND_WORD = "mod";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the module of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "m/ [MOD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "m/ CS1101S";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Mod command has not been implemented yet.";

    @Override
    public CommandResult execute(Model model) throws CommandException{
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
