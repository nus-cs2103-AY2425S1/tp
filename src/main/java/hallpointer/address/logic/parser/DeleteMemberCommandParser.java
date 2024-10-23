package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.DeleteMemberCommand;
import hallpointer.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMemberCommand object
 */
public class DeleteMemberCommandParser implements Parser<DeleteMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns a DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteMemberCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMemberCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE), pe);
        }
    }

}
