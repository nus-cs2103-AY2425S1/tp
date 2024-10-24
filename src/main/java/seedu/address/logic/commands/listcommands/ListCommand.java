package seedu.address.logic.commands.listcommands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Represents a command that lists data in the application.
 */
public abstract class ListCommand extends Command {

    // Common prefix for all list commands
    public static final String COMMAND_PREFIX = "list";

    /**
     * Executes the list command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public abstract CommandResult execute(Model model);
}
