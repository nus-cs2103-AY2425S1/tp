package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new ListCommand object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();

        if (trimmedArgs.isEmpty()) {
            return new ListCommand(false); // Normal list command
        } else if (trimmedArgs.equals("vip")) {
            return new ListCommand(true); // List only VIP persons
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.COMMAND_WORD));
        }
    }
}

