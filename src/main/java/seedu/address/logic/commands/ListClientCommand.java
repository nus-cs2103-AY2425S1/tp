package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists the details of clients in the MATER address book up to a specified integer provided.
 */
public class ListClientCommand extends Command {

    public static final String COMMAND_WORD = "list-client";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Placeholder for list-client");
    }

}
