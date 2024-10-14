package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.commands.ordercommands.ListOrderCommand;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListOrderCommand object
 */
public class ListOrderCommandParser implements Parser<ListOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListOrderCommand
     * and returns a ListOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListOrderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ListOrderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListOrderCommand.MESSAGE_USAGE), pe);
        }
    }

}
