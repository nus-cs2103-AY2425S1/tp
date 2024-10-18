package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class CloseClaimCommand extends Command {
    public static final String COMMAND_WORD = "closeClaim";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("");
    }
}
