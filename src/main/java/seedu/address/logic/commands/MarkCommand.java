package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Marks attendance of an existing person in the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("MESSAGE NOT IMPLEMENTED YET");
    }
}