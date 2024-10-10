package seedu.address.logic.parser;

import static seedu.address.logic.Messages.LIST_MESSAGE_INVALID_COMMAND;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Checks if input arguments are empty and executes ListCommand
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Checks if the given {@code String} of arguments is empty
     * and executes ListCommand object.
     * @throws ParseException if any user input is detected
     */
    public ListCommand parse(String args) throws ParseException {
        if (!args.isEmpty()) {
            throw new ParseException(LIST_MESSAGE_INVALID_COMMAND);
        }
        return new ListCommand();
    }

}
