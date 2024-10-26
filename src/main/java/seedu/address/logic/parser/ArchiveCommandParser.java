package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ArchiveCommand object
 */
public class ArchiveCommandParser implements Parser<ArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@link ArchiveCommand}
     * and returns an ArchiveCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ArchiveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args);
        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE), pe);
        }

        return new ArchiveCommand(index);
    }

}
