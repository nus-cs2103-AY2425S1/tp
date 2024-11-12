package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCarCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCarCommand object
 */
public class DeleteCarCommandParser implements Parser<DeleteCarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCarCommand
     * and returns a DeleteCarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCarCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCarCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCarCommand.MESSAGE_USAGE), pe);
        }
    }
}
