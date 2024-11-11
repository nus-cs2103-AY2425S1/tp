package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DemoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DemoteCommand object
 */
public class DemoteCommandParser implements Parser<DemoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DemoteCommand
     * and returns a DemoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DemoteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemoteCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemoteCommand.MESSAGE_USAGE), pe);
        }

        return new DemoteCommand(index);
    }
}
