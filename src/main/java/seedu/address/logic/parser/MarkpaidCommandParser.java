package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkPaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkPaidCommand object
 */
public class MarkpaidCommandParser implements Parser<MarkPaidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkPaidCommand
     * and returns a MarkPaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkPaidCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkPaidCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE), pe);
        }
    }
}
