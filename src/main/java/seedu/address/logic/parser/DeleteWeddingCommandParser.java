package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteWeddingCommand object
 */
public class DeleteWeddingCommandParser implements Parser<DeleteWeddingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteWeddingCommand
     * and returns a DeleteWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteWeddingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.startsWith("w/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWeddingCommand.MESSAGE_USAGE));
        }

        String weddingName = trimmedArgs.substring(2).trim();
        if (weddingName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteWeddingCommand.MESSAGE_USAGE));
        }

        return new DeleteWeddingCommand(weddingName);
    }
}
