package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds tags to an existing person in the address book
 */
public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds new tags to the person "
            + "identified by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friends"
            + PREFIX_TAG + "owesMoney";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("Tag Command not implemented yet");
    }
}
