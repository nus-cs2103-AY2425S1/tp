package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnpinCommand object
 */
public class UnpinCommandParser implements Parser<UnpinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnpinCommand
     * and returns a UnpinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnpinCommand parse(String args) throws ParseException {
        try {
            List<Index> indices = ParserUtil.parseIndexes(args);
            return new UnpinCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE), pe);
        }
    }

}
