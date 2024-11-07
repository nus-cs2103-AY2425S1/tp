package seedu.address.logic.parser;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and returns a new ListTransactionCommand object.
 */
public class ListTransactionCommandParser implements Parser<ListTransactionCommand> {
    private final Logger logger = LogsCenter.getLogger(ListTransactionCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the ListTransactionCommand
     * and returns a ListTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListTransactionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ListTransactionCommand(index);
        } catch (ParseException pe) {
            logger.fine("ParseException caused by missing or invalid index.");
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListTransactionCommand.MESSAGE_USAGE)
            );
        }
    }
}
