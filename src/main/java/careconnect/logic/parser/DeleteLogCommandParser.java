package careconnect.logic.parser;

import static careconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import careconnect.commons.core.index.Index;
import careconnect.logic.commands.DeleteLogCommand;
import careconnect.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteLogCommandParser implements Parser<DeleteLogCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLogCommand
     * and returns a DeleteLogCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLogCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_LOG_INDEX);

        Index personIndex;
        Index logIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLogCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_LOG_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLogCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_LOG_INDEX);

        logIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_LOG_INDEX).get());
        return new DeleteLogCommand(personIndex, logIndex);
    }

}
