package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new HistoryCommand object
 */
public class HistoryCommandParser implements Parser<HistoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HistoryCommand
     * and returns a HistoryCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public HistoryCommand parse(String args) throws ParseException {
        if (ParserUtil.isParsingIndex(args)) {
            Index index = ParserUtil.parseIndex(args);
            return new HistoryCommand(index);
        } else if (ParserUtil.isParsingNric(args)) {
            Nric nric = ParserUtil.parseNric(args);
            return new HistoryCommand(nric);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HistoryCommand.MESSAGE_USAGE));
        }
    }
}
