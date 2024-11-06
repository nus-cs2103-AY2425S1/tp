package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.filename.Filename;
import seedu.address.logic.commands.LoadArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoadArchiveCommand object
 */
public class LoadArchiveCommandParser implements Parser<LoadArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoadArchiveCommand
     * and returns a LoadArchiveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadArchiveCommand parse(String args) throws ParseException {
        Filename filename;
        try {
            filename = ParserUtil.parseFilename(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    pe.getMessage() + "\n\n" + LoadArchiveCommand.MESSAGE_USAGE), pe);
        }
        return new LoadArchiveCommand(filename);
    }
}
