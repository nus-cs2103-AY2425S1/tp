package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnarchiveCommand object
 */
public class UnarchiveCommandParser implements Parser<UnarchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@link UnarchiveCommand}
     * and returns an UnarchiveCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnarchiveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args);
        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE), pe);
        }

        return new UnarchiveCommand(index);
    }

}
