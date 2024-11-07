package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.filename.Filename;
import seedu.address.logic.commands.DeleteArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteArchiveCommand object
 */
public class DeleteArchiveCommandParser implements Parser<DeleteArchiveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteArchiveCommand
     * and returns a DeleteArchiveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteArchiveCommand parse(String args) throws ParseException {
        Filename filename;
        try {
            filename = ParserUtil.parseFilename(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    pe.getMessage() + "\n\n" + DeleteArchiveCommand.MESSAGE_USAGE), pe);
        }
        return new DeleteArchiveCommand(filename);
    }
}
