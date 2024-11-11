package seedu.ddd.logic.commands.add;

import seedu.ddd.logic.commands.Command;

/**
 * This interface provides constants for add command-related keywords and flags.
 */
public abstract class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String FLAG_PARSE_ERROR = "to add a client contact, vendor contact, or event respectively.";

}
