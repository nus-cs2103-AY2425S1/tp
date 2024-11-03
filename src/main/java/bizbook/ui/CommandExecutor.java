package bizbook.ui;

import bizbook.logic.Logic;
import bizbook.logic.commands.CommandResult;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.logic.parser.exceptions.ParseException;
/**
 * Represents a function that can execute commands.
 */
@FunctionalInterface
public interface CommandExecutor {
    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;
}
