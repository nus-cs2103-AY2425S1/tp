package seedu.address.logic.parser;

import seedu.address.commons.core.filename.Filename;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ArchiveCommand object
 */
public class ArchiveCommandParser implements Parser<ArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ArchiveCommand
     * and returns a ArchiveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ArchiveCommand parse(String args) throws ParseException {
        Filename filename = ParserUtil.parseFilename(args);
        return new ArchiveCommand(filename);
    }
}
