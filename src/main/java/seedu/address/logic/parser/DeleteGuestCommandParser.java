package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGuestCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteGuestCommand object
 */
public class DeleteGuestCommandParser implements Parser<DeleteGuestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGuestCommand
     * and returns a DeleteGuestCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGuestCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteGuestCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGuestCommand.MESSAGE_USAGE), pe);
        }
    }

}
