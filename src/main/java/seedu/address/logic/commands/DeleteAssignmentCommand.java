package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Deletes an assignment.
 */
public class DeleteAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "deletea";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an assignment for a student. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ASSIGNMENT + "ASSIGNMENT "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ASSIGNMENT + "Math Quiz ";

    public static final String MESSAGE_SUCCESS = "Deleted Assignment : %1$s";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
