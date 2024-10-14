package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMeetUpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMeetUpCommand object
 */
public class DeleteMeetUpCommandParser implements Parser<DeleteMeetUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMeetUpCommand
     * and returns a DeleteMeetUpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMeetUpCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMeetUpCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetUpCommand.MESSAGE_USAGE), pe);
        }
    }

}
