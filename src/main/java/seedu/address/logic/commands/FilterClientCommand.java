package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Filters clients based on their name.
 */
public class FilterClientCommand extends Command {

    /** The command word to trigger the filtering action. */
    public static final String COMMAND_WORD = "filterclient";

    /**
     * Usage information for the filterclient command.
     * Provides a description of the command's purpose and the format for entering client names.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the clients based on their name. "
            + "Parameters: NAME (must be a string) "
            + PREFIX_FILTER + "[NAME]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILTER + " Bob";

    /** Message indicating the command is not yet implemented. */
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "filterclient command not implemented yet";

    /**
     * Executes the command to filter clients based on the provided name.
     *
     * @param model The model which contains the client data to be filtered.
     * @return The result of the command execution.
     * @throws CommandException If the command is not yet implemented.
     */
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
