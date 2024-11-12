package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */

public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand("all");
        }

        if (trimmedArgs.equalsIgnoreCase("employees")) {
            return new ListCommand("employees");
        }

        if (trimmedArgs.equalsIgnoreCase("clients")) {
            return new ListCommand("clients");
        }

        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

}
