package seedu.address.logic.commands.listcommands;

import seedu.address.logic.commands.Command;

/**
 * Represents a command that lists data in the application.
 */
public abstract class ListCommand extends Command {

    // Common prefix for all list commands
    public static final String COMMAND_PREFIX = "list";

}
