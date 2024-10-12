package seedu.address.ui;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Represents a function that can execute commands.
 */
@FunctionalInterface
public interface CommandExecutor {
    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;
}